# 【SpringBoot】EC Web Site

## 工夫した点
・カートの管理の仕方
  1) Cart(CartItem(Itemクラスとnumberという変数)のList)という具合にインスタンスを階層のように持たせて管理。

  2) カート内の商品の削除・個数変更は、Cartインスタンスの中に専用のメソッドを用意して行った。
  
  3) Cartインスタンスはセッションで管理する。コントローラのAutowiredが実行されるとともにセッションに登録されるようにした。Cartクラスには@SessionScopeアノテーションを使用した。

・商品一覧
  1) 商品の詳細をモーダルで表示する。

     i) Modalの表示にはBootStrapを使用した。

        商品詳細の表示ボタンに、Thymeleafのth:attr属性を使って、商品名、商品詳細、詳細写真のURLのデータを持たせておいた。

        該当ボタンを押下すると、モーダルウィンドウに前述の3つのデータを渡して表示するようにした。
     
  3) 商品一覧はDBに登録した商品をリストの形式で取り出し、繰り返し処理で全てを一覧として表示した。

## 機能
・事前にDBに登録した商品を商品一覧から選択して購入することができる。カート画面から、それぞれ、配送情報→決済情報→最終確認→購入決定の順で遷移する。配送情報と決済情報は該当項目を入力しないとバリデーションが作動する。

・ユーザー登録機能 ： 事前にユーザーの配送情報・連絡先や決済情報を登録しておくと、購入手続き時の配送情報や決済情報の入力を簡略化できる。

・注文履歴検索機能 : 注文IDを画面入力することで、注文した商品の情報や配送先や決済情報を表示する。


## 実装環境
バックエンド : Java, MySQL

フロントエンド : HTML, CSS(BootStrap4), JavaScript

フレームワーク : SpringBoot


## 解説動画
### 商品購入


https://github.com/r-norinaga/EC-site/assets/138180446/2a9462d0-b685-46a8-bd6e-cdf7fe181e0e



### 注文履歴検索



https://github.com/r-norinaga/EC-site/assets/138180446/ebcb2700-7181-4250-b386-27db9a0db855


### 注文キャンセル



https://github.com/r-norinaga/EC-site/assets/138180446/bbeb21df-4f7c-410d-b136-3a6287590d01




### ユーザー登録、ログイン、登録したユーザー情報の確認(ログインユーザーのみ)




https://github.com/r-norinaga/EC-site/assets/138180446/622d7f09-aad1-4117-8139-c05a11c428b3



### ユーザー登録未入力(バリデーション)






https://github.com/r-norinaga/EC-site/assets/138180446/9ed14b13-946d-4a6d-b281-3822c30f40fb




### ユーザー情報の更新・削除




https://github.com/r-norinaga/EC-site/assets/138180446/29cb74da-cc8d-4424-99c8-d044b3f34cc7

