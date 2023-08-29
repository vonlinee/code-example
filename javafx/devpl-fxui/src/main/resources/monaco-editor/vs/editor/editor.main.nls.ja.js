/*!-----------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Version: 0.8.2(undefined)
 * Released under the MIT license
 * https://github.com/Microsoft/vscode/blob/master/LICENSE.txt
 *-----------------------------------------------------------*/

define("vs/editor/editor.main.nls.ja", {
	"vs/base/browser/ui/actionbar/actionbar": [
		"{0} ({1})",
	],
	"vs/base/browser/ui/aria/aria": [
		"{0} (再発)",
	],
	"vs/base/browser/ui/findinput/findInput": [
		"入力",
	],
	"vs/base/browser/ui/findinput/findInputCheckboxes": [
		"大文字と小文字を区別する",
		"単語単位で検索する",
		"正規表現を使用する",
	],
	"vs/base/browser/ui/inputbox/inputBox": [
		"エラー: {0}",
		"警告: {0}",
		"情報: {0}",
	],
	"vs/base/common/json": [
		"シンボルが無効です",
		"数値表示形式が無効です",
		"プロパティ名が必要です",
		"値が必要です",
		"コロンが必要です",
		"コンマが必要です",
		"右中かっこが必要です",
		"右角かっこが必要です",
		"ファイルの終わりが必要です",
	],
	"vs/base/common/severity": [
		"エラー",
		"警告",
		"情報",
	],
	"vs/base/parts/quickopen/browser/quickOpenModel": [
		"{0}、選択",
		"選択",
	],
	"vs/base/parts/quickopen/browser/quickOpenWidget": [
		"クイック選択。入力すると結果が絞り込まれます。",
		"クイック選択",
	],
	"vs/base/parts/tree/browser/treeDefaults": [
		"折りたたむ",
	],
	"vs/editor/common/config/commonEditorConfig": [
		"エディター",
		"フォント ファミリを制御します。",
		"フォントの太さを制御します。",
		"フォント サイズをピクセル単位で制御します。",
		"行の高さを制御します。fontSize に基づいて lineHeight を計算する場合には、0 を使用します。",
		"行番号の表示を制御します。使用可能な値は、\'on\'、\'off\'、および \'relative\' です。\'relative\' は現在のカーソル位置からの行数を示します。",
		"垂直ルーラーを表示する列",
		"単語に関連したナビゲーションまたは操作を実行するときに、単語の区切り文字として使用される文字",
		"1 つのタブに相当するスペースの数。`editor.detectIndentation` がオンの場合、この設定はファイル コンテンツに基づいて上書きされます。",
		"\'number\' が必要です。`editor.detectIndentation` 設定によって値 \"auto\" が置き換えられていることに注意してください。",
		"Tab キーを押すとスペースが挿入されます。`editor.detectIndentation` がオンの場合、この設定はファイル コンテンツに基づいて上書きされます。",
		"\'boolean\' が必要です。`editor.detectIndentation` 設定によって値 \"auto\" が置き換えられていることに注意してください。",
		"ファイルを開くと、そのファイルの内容に基づいて `editor.tabSize` と `editor.insertSpaces` が検出されます。",
		"選択範囲の角を丸くするかどうかを制御します",
		"エディターで最後の行を越えてスクロールするかどうかを制御します",
		"ミニマップを表示するかどうかを制御します",
		"行に (カラー ブロックではなく) 実際の文字を表示します",
		"表示するミニマップの最大幅を特定の桁数に制限します",
		"行を折り返しません。",
		"行をビューポートの幅で折り返します。",
		"行を \'editor.wordWrapColumn\' で折り返します。",
		"ビューポートと \'editor.wordWrapColumn\' の最小値で行を折り返します。",
		"行の折り返し方法を制御します。次の値を指定できます。\n - \'off\' (折り返さない)\n - \'on\' (ビューポート折り返し)\n - \'wordWrapColumn\' (\'editor.wordWrapColumn\' で折り返し)\n - \'bounded\' (ビューポートと \'editor.wordWrapColumn\' の最小値で折り返し)。",
		"\'editor.wordWrap\' が \'wordWrapColumn\' または \'bounded\' の場合に、エディターの折り返し桁を制御します。",
		"折り返し行のインデントを制御します。\'none\'、\'same\'、または \'indent\' のいずれかを指定できます。",
		"マウス ホイール スクロール イベントの `deltaX` と `deltaY` で使用される乗数",
		"文字列内でクイック候補を有効にします。",
		"コメント内でクイック候補を有効にします。",
		"文字列およびコメント外でクイック候補を有効にします。",
		"入力中に候補を自動的に表示するかどうかを制御します",
		"クイック候補が表示されるまでの待ち時間 (ミリ秒) を制御します",
		"パラメーター ヒントを有効にする",
		"エディターで左角かっこの後に自動的に右角かっこを挿入するかどうかを制御します",
		"エディターで入力後に自動的に行の書式設定を行うかどうかを制御します",
		"貼り付けた内容がエディターにより自動的にフォーマットされるかどうかを制御します。フォーマッタを使用可能にする必要があります。また、フォーマッタがドキュメント内の範囲をフォーマットできなければなりません。",
		"トリガー文字の入力時に候補が自動的に表示されるようにするかどうかを制御します",
		"\'Tab\' キーに加えて \'Enter\' キーで候補を受け入れるかどうかを制御します。改行の挿入や候補の反映の間であいまいさを解消するのに役立ちます。",
		"コミット文字で候補を受け入れるかどうかを制御します。たとえば、JavaScript ではセミコロン (\';\') をコミット文字にして、候補を受け入れてその文字を入力することができます。",
		"他の修正候補と一緒にスニペットを表示するかどうか、およびその並び替えの方法を制御します。",
		"選択範囲を指定しないでコピーする場合に現在の行をコピーするかどうかを制御します。",
		"ドキュメント内の単語に基づいて入力候補を計算するかどうかを制御します。",
		"候補のウィジェットのフォント サイズ",
		"候補のウィジェットの行の高さ",
		"エディターで選択範囲に類似する一致箇所を強調表示するかどうかを制御します",
		"エディターでセマンティック シンボルの出現箇所を強調表示するかどうかを制御します",
		"概要ルーラーの同じ位置に表示できる装飾の数を制御します",
		"概要ルーラーの周囲に境界線が描画されるかどうかを制御します。",
		"カーソルのアニメーション スタイルを制御します。指定できる値は \'blink\'、\'smooth\'、\'phase\'、\'expand\'、\'solid\' です",
		"Ctrl キーを押しながらマウス ホイールを使用してエディターのフォントをズームします",
		"カーソルのスタイルを制御します。指定できる値は \'block\'、\'block-outline\'、\'line\'、\'line-thin\'、\'underline\'、\'underline-thin\' です",
		"フォントの合字を使用します",
		"概要ルーラーでカーソルを非表示にするかどうかを制御します。",
		"エディターで空白文字を表示する方法を制御します。\'none\'、\'boundary\' および \'all\' が使用可能です。\'boundary\' オプションでは、単語間の単一スペースは表示されません。",
		"エディターで制御文字を表示する必要があるかどうかを制御します",
		"エディターでインデントのガイドを表示する必要があるかどうかを制御します",
		"エディターが現在の行をどのように強調表示するかを制御します。考えられる値は \'none\'、\'gutter\'、\'line\'、\'all\' です。",
		"エディターでコード レンズを表示するかをどうかを制御する",
		"エディターでコードの折りたたみを有効にするかどうかを制御します",
		"かっこを選択すると、対応するかっこを強調表示します。",
		"エディターで縦のグリフ余白が表示されるかどうかを制御します。ほとんどの場合、グリフ余白はデバッグに使用されます。",
		"空白の挿入や削除はタブ位置に従って行われます",
		"自動挿入された末尾の空白を削除する",
		"エディターのコンテンツをダブルクリックするか、Esc キーを押しても、ピーク エディターを開いたままにします。",
		"ドラッグ アンド ドロップによる選択範囲の移動をエディターが許可する必要があるかどうかを制御します。",
		"差分エディターが差分を横に並べて表示するか、行内に表示するかを制御します",
		"差分エディターが、先頭または末尾の空白の変更を差分として表示するかどうかを制御します。",
		"差分エディターが追加/削除された変更に +/- インジケーターを示すかどうかを制御します",
		"Linux の PRIMARY クリップボードをサポートするかどうかを制御します。",
	],
	"vs/editor/common/config/defaultConfig": [
		"エディターのコンテンツ",
	],
	"vs/editor/common/controller/cursor": [
		"コマンドの実行中に予期しない例外が発生しました。",
	],
	"vs/editor/common/model/textModelWithTokens": [
		"入力のトークン化中にモードが失敗しました。",
	],
	"vs/editor/common/modes/modesRegistry": [
		"プレーンテキスト",
	],
	"vs/editor/common/services/bulkEdit": [
		"この間に次のファイルが変更されました: {0}",
		"Made no edits",
		"Made {0} text edits in {1} files",
		"Made {0} text edits in one file",
	],
	"vs/editor/common/services/modeServiceImpl": [
		"言語の宣言を提供します。",
		"言語の ID。",
		"言語の名前のエイリアス。",
		"言語に関連付けられているファイルの拡張子。",
		"言語に関連付けられたファイル名。",
		"言語に関連付けられたファイル名の glob パターン。",
		"言語に関連付けられている MIME の種類。",
		"言語のファイルの最初の行に一致する正規表現。",
		"言語の構成オプションを含むファイルへの相対パス。",
	],
	"vs/editor/common/services/modelServiceImpl": [
		"[{0}]\n{1}",
		"[{0}] {1}",
	],
	"vs/editor/common/view/editorColorRegistry": [
		"カーソル位置の行を強調表示する背景色。",
		"カーソル位置の行の境界線を強調表示する背景色。",
		"Quick Open 機能や検索機能などによって強調表示された範囲の背景色。",
		"エディターのカーソルの色。",
		"エディターのスペース文字の色。",
		"エディター インデント ガイドの色。",
		"エディターの行番号の色。",
	],
	"vs/editor/contrib/bracketMatching/common/bracketMatching": [
		"ブラケットへ移動",
	],
	"vs/editor/contrib/caretOperations/common/caretOperations": [
		"キャレットを左に移動",
		"キャレットを右に移動",
	],
	"vs/editor/contrib/caretOperations/common/transpose": [
		"文字の入れ替え",
	],
	"vs/editor/contrib/clipboard/browser/clipboard": [
		"切り取り",
		"コピー",
		"貼り付け",
		"構文を強調表示してコピー",
	],
	"vs/editor/contrib/comment/common/comment": [
		"行コメントの切り替え",
		"行コメントの追加",
		"行コメントの削除",
		"ブロック コメントの切り替え",
	],
	"vs/editor/contrib/contextmenu/browser/contextmenu": [
		"エディターのコンテキスト メニューの表示",
	],
	"vs/editor/contrib/find/browser/findWidget": [
		"検索",
		"検索",
		"前の一致項目",
		"次の一致項目",
		"選択範囲を検索",
		"閉じる",
		"置換",
		"置換",
		"置換",
		"すべて置換",
		"置換モードの切り替え",
		"最初の 999 の結果だけを強調表示しますが、テキスト全体を検索します。",
		"{1} の {0}",
		"結果なし",
	],
	"vs/editor/contrib/find/common/findController": [
		"検索",
		"次を検索",
		"前を検索",
		"次の選択項目を検索",
		"前の選択項目を検索",
		"選択した項目を次の一致項目に追加",
		"選んだ項目を前の一致項目に追加する",
		"最後に選択した項目を次の一致項目に移動",
		"最後に選んだ項目を前の一致項目に移動する",
		"一致するすべての出現箇所を選択します",
		"すべての出現箇所を変更",
	],
	"vs/editor/contrib/folding/browser/folding": [
		"展開",
		"再帰的に展開する",
		"折りたたみ",
		"再帰的に折りたたむ",
		"すべて折りたたみ",
		"すべて展開",
		"折りたたみレベル {0}",
	],
	"vs/editor/contrib/format/browser/formatActions": [
		"行 {0} で 1 つの書式設定を編集",
		"行 {1} で {0} 個の書式設定を編集",
		"行 {0} と {1} の間で 1 つの書式設定を編集",
		"行 {1} と {2} の間で {0} 個の書式設定を編集",
		"ドキュメントのフォーマット",
		"選択範囲のフォーマット",
	],
	"vs/editor/contrib/goToDeclaration/browser/goToDeclaration": [
		"\'{0}\' の定義は見つかりません",
		"定義が見つかりません",
		" - {0} の定義",
		"定義へ移動",
		"定義を横に開く",
		"定義をここに表示",
		"\'{0}\' の実装が見つかりません",
		"実装が見つかりません",
		" – {0} implementations",
		"実装に移動",
		"実装のプレビュー",
		"\'{0}\' の型定義が見つかりません",
		"型定義が見つかりません",
		" – {0} type definitions",
		"型定義へ移動",
		"型定義を表示",
		"クリックして、{0} の定義を表示します。",
	],
	"vs/editor/contrib/gotoError/browser/gotoError": [
		"({0}/{1})",
		"次のエラーまたは警告へ移動",
		"前のエラーまたは警告へ移動",
		"エディターのマーカー ナビゲーション ウィジェットのエラーの色。",
		"エディターのマーカー ナビゲーション ウィジェットの警告の色。",
		"エディターのマーカー ナビゲーション ウィジェットの背景。",
	],
	"vs/editor/contrib/hover/browser/hover": [
		"ホバーの表示",
		"ホバーが表示されているワードの下を強調表示します。",
		"エディター ホバーの背景色。",
		"エディター ホバーの境界線の色。",
	],
	"vs/editor/contrib/hover/browser/modesContentHover": [
		"読み込んでいます...",
	],
	"vs/editor/contrib/inPlaceReplace/common/inPlaceReplace": [
		"前の値に置換",
		"次の値に置換",
	],
	"vs/editor/contrib/inspectTokens/browser/inspectTokens": [
		"Developer: Inspect Tokens",
	],
	"vs/editor/contrib/linesOperations/common/linesOperations": [
		"行を上へコピー",
		"行を下へコピー",
		"行を上へ移動",
		"行を下へ移動",
		"行を昇順に並べ替え",
		"行を降順に並べ替え",
		"末尾の空白のトリミング",
		"行の削除",
		"行のインデント",
		"行のインデント解除",
		"残りをすべて削除する",
		"右側をすべて削除",
		"行をつなげる",
		"カーソルの周囲の文字を入れ替える",
		"大文字に変換",
		"小文字に変換",
	],
	"vs/editor/contrib/links/browser/links": [
		"command キーを押しながらクリックしてリンク先を表示",
		"Ctrl キーを押しながらクリックしてリンク先を表示",
		"申し訳ありません。このリンクは形式が正しくないため開くことができませんでした: {0}",
		"申し訳ありません。このリンクはターゲットが存在しないため開くことができませんでした。",
		"リンクを開く",
	],
	"vs/editor/contrib/multicursor/common/multicursor": [
		"カーソルを上に挿入",
		"カーソルを下に挿入",
		"選択した行から複数のカーソルを作成",
	],
	"vs/editor/contrib/parameterHints/browser/parameterHints": [
		"パラメーター ヒントをトリガー",
	],
	"vs/editor/contrib/parameterHints/browser/parameterHintsWidget": [
		"{0}、ヒント",
	],
	"vs/editor/contrib/quickFix/browser/quickFixCommands": [
		"Show Fixes ({0})",
		"Show Fixes",
		"Quick Fix",
	],
	"vs/editor/contrib/quickOpen/browser/gotoLine": [
		"行 {0} 列 {1} へ移動",
		"行 {0} へ移動",
		"移動先の行番号を 1 ～ {0} の範囲で入力してください",
		"移動先の列を 1 ～ {0} の範囲で入力してください",
		"Go to line {0}",
		"オプションのコロンと列番号の前に移動先の行番号を入力してください",
		"指定行へ移動...",
	],
	"vs/editor/contrib/quickOpen/browser/quickCommand": [
		"{0}, commands",
		"実行する操作の名前を入力してください",
		"コマンド パレット",
	],
	"vs/editor/contrib/quickOpen/browser/quickOutline": [
		"{0}, symbols",
		"移動先の識別子の名前を入力してください",
		"シンボルへ移動...",
		"シンボル ({0})",
		"モジュール ({0})",
		"クラス ({0})",
		"インターフェイス ({0})",
		"メソッド ({0})",
		"関数 ({0})",
		"プロパティ ({0})",
		"変数 ({0})",
		"変数 ({0})",
		"コンストラクター ({0})",
		"呼び出し ({0})",
	],
	"vs/editor/contrib/referenceSearch/browser/referenceSearch": [
		" - {0} の参照",
		"すべての参照の検索",
	],
	"vs/editor/contrib/referenceSearch/browser/referencesController": [
		"読み込んでいます...",
	],
	"vs/editor/contrib/referenceSearch/browser/referencesWidget": [
		"ファイルを解決できませんでした。",
		"{0} 個の参照",
		"{0} 個の参照",
		"1 reference in {0}",
		"{0} references in {1}",
		"reference in {0} on line {1} at column {2}",
		"プレビューを表示できません",
		"参照",
		"結果がありません",
		"Found {0} references",
		"参照",
		"ピーク ビューのタイトル領域の背景色。",
		"ピーク ビュー タイトルの色。",
		"ピーク ビューのタイトル情報の色。",
		"ピーク ビューの境界と矢印の色。",
		"ピーク ビュー結果リストの背景色。",
		"ピーク ビュー結果リストの一致したエントリの前景色。",
		"ピーク ビュー結果リストのファイル エントリの前景色。",
		"ピーク ビュー結果リストの選択済みエントリの背景色。",
		"ピーク ビュー結果リストの選択済みエントリの前景色。",
		"ピーク ビュー エディターの背景色。",
		"ピーク ビュー結果リストの一致した強調表示色。",
		"ピーク ビュー エディターの一致した強調表示色。",
	],
	"vs/editor/contrib/rename/browser/rename": [
		"No result.",
		"Successfully renamed \'{0}\' to \'{1}\'. Summary: {2}",
		"申し訳ありません。名前の変更を実行できませんでした。",
		"シンボルの名前を変更",
	],
	"vs/editor/contrib/rename/browser/renameInputField": [
		"名前変更入力。新しい名前を入力し、Enter キーを押してコミットしてください。",
	],
	"vs/editor/contrib/smartSelect/common/smartSelect": [
		"選択範囲を拡大",
		"選択範囲を縮小",
	],
	"vs/editor/contrib/suggest/browser/suggestController": [
		"Accepting \'{0}\' did insert the following text: {1}",
		"候補をトリガー",
	],
	"vs/editor/contrib/suggest/browser/suggestWidget": [
		"Background color of the suggest widget.",
		"Border color of the suggest widget.",
		"Color of the match highlight in the suggest widget.",
		"詳細を表示...{0}",
		"{0}、候補、詳細あり",
		"{0}、候補",
		"戻る",
		"読み込んでいます...",
		"候補はありません。",
		"{0}、受け入れ済み",
		"{0}、候補、詳細あり",
		"{0}、候補",
	],
	"vs/editor/contrib/toggleTabFocusMode/common/toggleTabFocusMode": [
		"Tab キーを切り替えるとフォーカスが移動します",
	],
	"vs/editor/contrib/wordHighlighter/common/wordHighlighter": [
		"変数の読み取りなど読み取りアクセス中のシンボルの背景色。",
		"変数への書き込みなど書き込みアクセス中のシンボルの背景色。",
	],
	"vs/editor/contrib/zoneWidget/browser/peekViewWidget": [
		"閉じる",
	],
	"vs/platform/configuration/common/configurationRegistry": [
		"既定の構成オーバーライド",
		"{0} 言語に対して上書きされるエディター設定を構成します。",
		"言語に対して上書きされるエディター設定を構成します。",
		"構成の設定を提供します。",
		"設定の概要です。このラベルは、設定ファイルでコメントの区切り文字として使用します。",
		"構成のプロパティの説明です。",
		"\'{0}\' を登録できません。これは、言語固有のエディター設定を記述するプロパティ パターン \'\\[.*\\]$\' に一致しています。\'configurationDefaults\' コントリビューションを使用してください。",
		"\'{0}\' を登録できません。このプロパティは既に登録されています。",
		"\'configuration.properties\' は、オブジェクトである必要があります",
		"設定すると、\'configuration.type\' は \'オブジェクトに設定されなければなりません",
		"\'configuration.title\' は、文字列である必要があります",
		"言語ごとに既定のエディター構成の設定を提供します。",
	],
	"vs/platform/extensions/common/extensionsRegistry": [
		"VS Code 拡張機能の場合、拡張機能と互換性のある VS Code バージョンを指定します。* を指定することはできません。たとえば、^0.10.5 は最小の VS Code バージョン 0.10.5 との互換性を示します。",
		"VS Code 拡張機能の公開元。",
		"VS Code ギャラリーで使用される拡張機能の表示名。",
		"VS Code ギャラリーで拡張機能の分類に使用されるカテゴリ。",
		"VS Code マーケットプレースで使用されるバナー。",
		"VS Code マーケットプレース ページ ヘッダー上のバナーの色。",
		"バナーで使用されるフォントの配色テーマ。",
		"このパッケージで表される VS Code 拡張機能のすべてのコントリビューション。",
		"Marketplace で Preview としてフラグが付けられるように拡張機能を設定します。",
		"VS Code 拡張機能のアクティブ化イベント。",
		"Marketplace の拡張機能ページのサイドバーに表示されるバッジの配列。",
		"バッジのイメージ URL。",
		"バッジのリンク。",
		"バッジの説明。",
		"他の拡張機能に対する依存関係。拡張機能の識別子は常に ${publisher}.${name} です。例: vscode.csharp。",
		"パッケージが VS Code 拡張機能として公開される前に実行されるスクリプト。",
		"128x128 ピクセルのアイコンへのパス。",
	],
	"vs/platform/keybinding/common/abstractKeybindingService": [
		"({0}) が押されました。2 番目のキーを待っています...",
		"キーの組み合わせ ({0}、{1}) はコマンドではありません。",
	],
	"vs/platform/keybinding/common/keybindingLabels": [
		"Ctrl",
		"Shift",
		"Alt",
		"Windows",
		"Control",
		"Shift",
		"Alt",
		"command",
		"Control",
		"Shift",
		"Alt",
		"Windows",
	],
	"vs/platform/message/common/message": [
		"閉じる",
		"後で",
		"キャンセル",
	],
	"vs/platform/theme/common/colorRegistry": [
		"無効な色形式です。#RRGGBB または #RRGGBBAA を使用してください",
		"ワークベンチで使用する色。",
		"全体の前景色。この色は、コンポーネントによってオーバーライドされていない場合にのみ使用されます。",
		"フォーカスされた要素の輪郭全体の色。この色は、コンポーネントによってオーバーライドされていない場合にのみ使用されます。",
		"ハイ コントラスト テーマが有効になっている場合にコンポーネントを見分けるための境界線の色。",
		"ハイ コントラスト テーマが有効になっている場合のアクティブなコンポーネントの輪郭の色。",
		"入力ボックスの背景。",
		"入力ボックスの前景。",
		"入力ボックスの境界線。",
		"入力フィールドのアクティブ オプションの境界線の色。",
		"ドロップダウンの背景。",
		"ドロップダウンの前景。",
		"ドロップダウンの境界線。",
		"List/Tree focus background when active.",
		"List/Tree focus background when inactive.",
		"List/Tree selection background when active.",
		"List/Tree selection foreground when active.",
		"List/Tree focus and selection background.",
		"List/Tree focus and selection foreground.",
		"List/Tree selection background when inactive.",
		"List/Tree hover background.",
		"List/Tree drag and drop background.",
		"List/Tree focus outline color when active.",
		"List/Tree focus outline color when inactive.",
		"List/Tree selection outline color.",
		"List/Tree hover outline color.",
		"エディターの背景色。",
		"エディターの既定の前景色。",
		"エディターの選択範囲の色。",
		"非アクティブなエディターの選択範囲の色。",
		"選択範囲と同じコンテンツの領域の色。",
		"現在の検索一致項目の色。",
		"他の検索一致項目の色。",
		"検索を制限する範囲の色。",
		"アクティブなリンクの色。",
		"リンクの色。",
		"Background color of editor widgets, such as find/replace.",
		"Shadow color of editor widgets such as find/replace.",
	]
});