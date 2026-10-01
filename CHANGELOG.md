Changelog
=========

1.2.3
-----
- チェンジログ(CHANGELOG.md / CHANGELOG_en.md)を同梱

1.2.1
-----
- 設定画面のビルドエラーを修正(Minecraft 26.3のAPIに合わせて、
  タイトル描画と画面の切り替え処理を変更)

1.2.0
-----
- Mod Menu(21.0.0)に対応
  - MODの一覧から設定画面を開き、倍率をスライダー(0.1刻み)で変更可能に
  - 設定画面に「初期値に戻す」ボタンを追加
- Mod Menu の概要・説明の翻訳キーが正しくなかった問題を修正
- Minecraft 26.3 専用に変更(26.1 / 26.2 向けのビルド切り替えを廃止)
- Fabric API を 0.161.0+26.3 に更新
- README_versions.txt / README_versions_en.txt を廃止し、
  ビルド方法をREADMEに統合

1.1.1
-----
- Minecraft 26.3 正式版に対応

1.0.3
-----
- 複数バージョン対応の案内(README_versions.txt / README_versions_en.txt)を追加

1.0.2
-----
- fabric.mod.json の対応Minecraftバージョン指定を修正
  (26.2以上必須になっていたのを撤廃し、複数バージョンで動作するように)

1.0.1
-----
- ビルド時に実際どのMinecraftバージョン向けか確認できるよう、
  ビルドログに表示を追加

1.0.0
-----
- 初回リリース
- 地図の可視化範囲を1.0〜4.0倍で拡張する機能
- config/mapviewrange.json とゲーム内コマンドでの倍率変更に対応
