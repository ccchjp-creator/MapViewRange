Map View Range (Ver.1.2.3)
============================

概要
----
地図を持って歩いたときに、周辺が塗られる(可視化される)範囲を広げるMOD。

vanillaは MapItem#update(...) の中で
    int radius = 128 / scale;
という式で「1回の更新でプレイヤー周辺を塗る半径」を決めており、
拡大した(scaleが大きい)地図ほどこのradiusが小さくなるため、
歩いても全然埋まらない、という体感になっていました。

このMODは、その式の「128」の部分だけを設定した倍率倍にして
radius自体を広げています。塗る処理自体は元々ティックごとに少しずつ
行う仕組みのままなので、一括で埋めるような重さは発生しません。

倍率の変更方法
--------------
0. Mod Menu(Ver.1.2.3で追加)
   Mod Menu(21.0.0)を入れていれば、MODの一覧から「Map View Range」を
   選んで設定ボタンを押すと、スライダーで倍率を変更できます
   (0.1刻み、「初期値に戻す」ボタン付き。画面を閉じるとconfigに保存)。
   Mod Menuは必須ではありません。入っていなくても通常どおり動きます。

1. コンフィグファイル
   config/mapviewrange.json の "radiusMultiplier" を書き換える
   (1.0〜4.0の範囲。ゲーム起動時に読み込まれる)

   例:
   {
     "radiusMultiplier": 3.0
   }

2. ゲーム内コマンド(要OP権限2、シングルプレイならホストは通常OK)
   - 現在の倍率を確認:  /mapviewrange radius
   - 倍率を変更(即時反映・configにも保存): /mapviewrange radius 3.0
   ※ 指定できる範囲は 1.0〜4.0 です。それ以外の値はコマンド側で弾かれます。

目安
----
- 1.0倍: vanilla標準のまま
- 2.0倍: 動作確認済み。負荷は軽く、体感でも十分速く感じるはず
- 3.0〜4.0倍: PCスペックが高ければ試す価値あり。重く感じたら
  コマンドで即座に下げられるので、その場で調整してみてください

対応バージョン
--------------
- Minecraft: 26.3 (難読化なし。Ver.1.2.3から26.3専用です)
- Fabric Loader: 0.19.5
- Fabric API: 0.161.0+26.3
- Mod Menu: 21.0.0 (任意)
- Fabric Loom: 1.17-SNAPSHOT (プラグインID net.fabricmc.fabric-loom)
- Java: 25
※ 26.1 / 26.2向けが必要な場合は、Ver.1.0.3までの配布物を使ってください。

導入方法
--------
1. Fabric Loader 0.19.5 (MC26.3向け) をインストール
2. Fabric API を mods フォルダに入れる
3. (任意)Mod Menu 21.0.0 を mods フォルダに入れる
4. このMODのjarを mods フォルダに入れる
   (mapinstafillと併用しても競合しません。別のメソッドを触っています)

ビルド方法
----------
プロジェクトフォルダで次を実行します。
  ./gradlew build
出来上がるjar: build/libs/mapviewrange-26.3-<mod_version>.jar
(<mod_version>は gradle.properties の mod_version。例: 1.2.3)
動作確認: ./gradlew runClient (Mod Menuも一緒に起動します)

既知の制約
----------
- MapItem#update のシグネチャ・128という定数の出現順が実際のソースと
  食い違っていた場合、狙った場所と違う定数を書き換えてしまう可能性があります。
- コマンドに権限チェックは付けていません(誰でも実行できます)。
