package Assignment8;

/**
 * Simple Drawing Application
 * 簡単なお絵かきソフト
 * ・フリーハンド，直線，四角，楕円の描画機能
 * ・四角と楕円は左下方向のみ
 * ・色などの変更機能は無し
 *
 * @author fukai
 */
import java.awt.*;
import java.awt.event.*;

import java.util.Collections;
import java.util.List;

import java.lang.Math;


import javax.swing.JScrollBar;
import Assignment8.DrawingApli00.MyCanvas.ColorAdjustmentListener;


public class DrawingApli00 extends Frame implements ActionListener {
  // ■ フィールド変数
  Button bt1, bt2, bt3, bt4, bt5, bt6, btClear, btEraser; // フレームに配置するボタンの宣言
  Panel  pnl;                // ボタン配置用パネルの宣言
  MyCanvas mc;               // 別途作成した MyCanvas クラス型の変数の宣言

  MenuItem menuItem1_1, menuItem1_2, menuItem1_3, menuItem1_4, menuItem1_5;
  MenuItem menuItem2_1, menuItem2_2, menuItem2_3, menuItem2_4;

  JScrollBar redScrollBar, greenScrollBar, blueScrollBar;
  Color currentColor = Color.BLACK;
 


  

  // ■ main メソッド（スタート地点）
  public static void main(String [] args) {
    DrawingApli00 da = new DrawingApli00(); 
  }

  
  // ■ コンストラクタ
  DrawingApli00() {
    super("Drawing Appli");
    this.setSize(800, 400); 

    pnl = new Panel();       // Panel のオブジェクト（実体）を作成
    mc = new MyCanvas(this); // mc のオブジェクト（実体）を作成

    this.setLayout(new BorderLayout(10, 10)); // レイアウト方法の指定
    this.add(pnl, BorderLayout.EAST);         // 右側に パネルを配置
    this.add(mc,  BorderLayout.CENTER);       // 左側に mc （キャンバス）を配置
                                         // BorerLayout の場合，West と East の幅は
                                         // 部品の大きさで決まる，Center は West と East の残り幅
    pnl.setLayout(new GridLayout(9,1));  // ボタンを配置するため，９行１列のグリッドをパネル上にさらに作成
    bt1 = new Button("フリーハンド"); bt1.addActionListener(this); pnl.add(bt1);// ボタンを順に配置
    bt2 = new Button("直線");      bt2.addActionListener(this); pnl.add(bt2);
    bt3 = new Button("四角"); bt3.addActionListener(this); pnl.add(bt3);
    bt4 = new Button("塗りつぶし四角");  bt4.addActionListener(this); pnl.add(bt4);
    bt5 = new Button("円");      bt5.addActionListener(this); pnl.add(bt5);
    bt6 = new Button("塗りつぶし円");  bt6.addActionListener(this); pnl.add(bt6);
    btClear = new Button("全消去"); btClear.addActionListener(this); pnl.add(btClear);
    btEraser = new Button("消しゴム"); btEraser.addActionListener(this); pnl.add(btEraser);
    

    
    MenuBar menubar = new MenuBar();
    Menu menu1 = new Menu("Color");
    Menu menu2 = new Menu("Width");
    menubar.add(menu1); menubar.add(menu2);
    menu1.addActionListener(this); menu2.addActionListener(this);

    menuItem1_1 = new MenuItem("Red");
    menuItem1_2 = new MenuItem("Blue");
    menuItem1_3 = new MenuItem("Green");
    menuItem1_4 = new MenuItem("Black");
    menuItem1_5 = new MenuItem("Yellow");
    
    menuItem2_1 = new MenuItem("極細");
    menuItem2_2 = new MenuItem("細");
    menuItem2_3 = new MenuItem("中");
    menuItem2_4 = new MenuItem("太");
    menu1.add(menuItem1_1); menu1.add(menuItem1_2); menu1.add(menuItem1_3); menu1.add(menuItem1_4); menu1.add(menuItem1_5);
    menu2.add(menuItem2_1); menu2.add(menuItem2_2); menu2.add(menuItem2_3); menu2.add(menuItem2_4); 
    menuItem1_1.addActionListener(this); menuItem1_2.addActionListener(this); menuItem1_3.addActionListener(this);
    menuItem1_4.addActionListener(this); menuItem1_5.addActionListener(this);
    menuItem2_1.addActionListener(this); menuItem2_2.addActionListener(this); menuItem2_3.addActionListener(this);
    menuItem2_4.addActionListener(this);

    int initialRed = currentColor.getRed();
    int initialGreen = currentColor.getGreen();
    int initialBlue = currentColor.getBlue();

    redScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, initialRed, 1, 0, 256);
    greenScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, initialGreen, 1, 0, 256);
    blueScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, initialBlue, 1, 0, 256);

    // スクロールバーのイベントリスナーの追加
    redScrollBar.addAdjustmentListener(new ColorAdjustmentListener(this));
    greenScrollBar.addAdjustmentListener(new ColorAdjustmentListener(this));
    blueScrollBar.addAdjustmentListener(new ColorAdjustmentListener(this));

    // パネルにスクロールバーを配置
    pnl.add(redScrollBar);
    pnl.add(greenScrollBar);
    pnl.add(blueScrollBar);

    
   

    this.setMenuBar(menubar);
    this.setVisible(true); //可視化
  }
  

  


  // ■ メソッド
  // ActionListener を実装しているため、例え内容が空でも必ず記述しなければならない
  public void actionPerformed(ActionEvent e){ // フレーム上で生じたイベントを e で取得
    if (e.getSource() == bt1)      // もしイベントが bt1 で生じたなら
      mc.mode=1;                   // モードを１に
    else if(e.getSource() == bt2) // もしイベントが bt2 で生じたなら
      mc.mode=2;                   // モードを２に
    else if (e.getSource() == bt3) // もしイベントが bt3 で生じたなら
      mc.mode=3;                   // モードを３に
    else if (e.getSource() == bt4) // もしイベントが bt4 で生じたなら
      mc.mode=4;                   // モードを４に
    else if (e.getSource() == bt5)
      mc.mode=5;
    else if(e.getSource() == bt6)
      mc.mode=6;
    else if(e.getSource() == btEraser)
      mc.mode=7;
    
    else if (e.getSource() == menuItem1_1) {
        // 色を赤に設定
        mc.gc.setColor(Color.RED);
        mc.repaint(); // 色の変更を即座に反映するために再描画
    } 
    else if (e.getSource() == menuItem1_2) {
        // 色を青に設定
        mc.gc.setColor(Color.BLUE);
        mc.repaint(); // 色の変更を即座に反映するために再描画
    } 
    else if (e.getSource() == menuItem1_3) {
        // 色を緑に設定
        mc.gc.setColor(Color.GREEN);
        mc.repaint(); // 色の変更を即座に反映するために再描画
    } 
    else if (e.getSource() == menuItem1_4) {
        // 色を黒に設定
        mc.gc.setColor(Color.BLACK);
        mc.repaint(); // 色の変更を即座に反映するために再描画
    } 
    else if (e.getSource() == menuItem1_5) {
        // 色を黄色に設定
        mc.gc.setColor(Color.YELLOW);
        mc.repaint(); // 色の変更を即座に反映するために再描画
    } 
    else if (e.getSource() == menuItem2_1) {
        // ペンの太さを1に設定
        Graphics2D g2d = (Graphics2D) mc.gc;
        g2d.setStroke(new BasicStroke(1));
    } 
    else if (e.getSource() == menuItem2_2) {
        // ペンの太さを5に設定
        Graphics2D g2d = (Graphics2D) mc.gc;
        g2d.setStroke(new BasicStroke(5));
    } 
    else if (e.getSource() == menuItem2_3) {
        // ペンの太さを10に設定
        Graphics2D g2d = (Graphics2D) mc.gc;
        g2d.setStroke(new BasicStroke(10));
    } 
    else if (e.getSource() == menuItem2_4) {
        // ペンの太さを20に設定
        Graphics2D g2d = (Graphics2D) mc.gc;
        g2d.setStroke(new BasicStroke(20));
    }
    else if(e.getSource() == btClear)
      mc.Clear();
      mc.repaint();
    
  
}

/**
 * Extended Canvas class for DrawingApli
 * [各モードにおける処理内容]
 * 1: free hand 
 *      pressed -> set x, y,  dragged  -> drawline & call repaint()
 * 2: draw line 
 *      pressed -> set x, y,  released -> drawline & call repaint()
 * 3: rect
 *      pressed -> set x, y,  released -> calc w, h & call repaint()
 * 4: circle
 *      pressed -> set x, y,  released -> calc w, h & call repaint()
 * 
 * @author fukai
 */
class MyCanvas extends Canvas implements MouseListener, MouseMotionListener {
  // ■ フィールド変数
 
  int x, y;   // mouse pointer position
  int px, py; // preliminary position
  int ow, oh; // width and height of the object
  int mode;   // drawing mode associated as below
  Image img = null;   // 仮の画用紙
  Graphics gc = null; // 仮の画用紙用のペン
  Dimension d; // キャンバスの大きさ取得用
  DrawingApli00 parent;

  int startX,startY,height,width;

 


  // ■ コンストラクタ
  MyCanvas(DrawingApli00 parent){
    mode=0;                       // initial value 
    this.setSize(500,400);        // キャンバスのサイズを指定
    addMouseListener(this);       // マウスのボタンクリックなどを監視するよう指定
    addMouseMotionListener(this); // マウスの動きを監視するよう指定
    this.parent = parent;
  }

  // ■ メソッド（オーバーライド）
  // フレームに何らかの更新が行われた時の処理
  public void update(Graphics g) {
    paint(g); // 下記の paint を呼び出す
  }

  // ■ メソッド（オーバーライド）
  public void paint(Graphics g) {
    d = getSize();   // キャンバスのサイズを取得
    if (img == null){ // もし仮の画用紙の実体がまだ存在しなければ
      img = createImage(d.width, d.height);
    } // 作成
    if (gc == null){ // もし仮の画用紙用のペン (GC) がまだ存在しなければ
      gc = img.getGraphics();
    }// 作成


     // Clear the canvas
    /*    gc.setColor(Color.WHITE);
        gc.fillRect(0, 0, d.width, d.height);
        gc.setColor(currentColor); */




    switch (mode){
    case 1: // モードが１の場合
      gc.drawLine(px, py, x, y); // 仮の画用紙に描画
      mc.Reset();
      break;
    case 2: // モードが２の場合
      gc.drawLine(px, py, x, y); // 仮の画用紙に描画
      mc.Reset();
      break;
    case 3: // モードが３の場合
     // gc.drawRect(px, py, ow, oh); // 仮の画用紙に描画
      startX = Math.min(px, x);
      startY = Math.min(py, y);
      width = Math.abs(x - px);
      height = Math.abs(y - py);
      gc.drawRect(startX, startY, width, height);
      mc.Reset();
      break;
    case 4: // モードが４の場合
    //  gc.fillRect(px, py, ow, oh); // 仮の画用紙に描画
      startX = Math.min(px, x);
      startY = Math.min(py, y);
      width = Math.abs(x - px);
      height = Math.abs(y - py);
      gc.fillRect(startX, startY, width, height);
      mc.Reset();
      break;
    case 5:
    //  gc.drawOval(px, py, ow, oh);
      startX = Math.min(px, x);
      startY = Math.min(py, y);
      width = Math.abs(x - px);
      height = Math.abs(y - py);
      gc.drawOval(startX, startY, width, height);
      mc.Reset();
      break;
    case 6:
    //  gc.fillOval(px, py, ow, oh);
      startX = Math.min(px, x);
      startY = Math.min(py, y);
      width = Math.abs(x - px);
      height = Math.abs(y - py);
      gc.fillOval(startX, startY, width, height);
      mc.Reset();
      break;
    case 7:
      gc.drawLine(px, py, x, y);
      gc.setColor(Color.WHITE);
      mc.Reset();
      break;
    }
    g.drawImage(img, 0, 0, this); // 仮の画用紙の内容を MyCanvas に描画
  }
  
  public void Clear(){
    x=0;y=0;px=0;py=0;ow=0;oh=0;
    startX=0;startY=0;height=0;width=0;
    img = null;
    gc = null;
    mode = 0;
  }

  public void Reset(){
    x=0; y=0; px=0; py=0; ow=0; oh=0;
    startX=0;startY=0;height=0;width=0;
  }

  static class ColorAdjustmentListener implements AdjustmentListener {
    DrawingApli00 parent;

    ColorAdjustmentListener(DrawingApli00 parent) {
        this.parent = parent;
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        // スクロールバーの値を取得
        int redValue = parent.redScrollBar.getValue();
        int greenValue = parent.greenScrollBar.getValue();
        int blueValue = parent.blueScrollBar.getValue();

        // 色を設定
        parent.currentColor = new Color(redValue, greenValue, blueValue);
        parent.mc.gc.setColor(parent.currentColor);
        parent.mc.repaint();
    }
}


  // ■ メソッド
  // 下記のマウス関連のメソッドは，MouseListener をインターフェースとして実装しているため
  // 例え使わなくても必ず実装しなければならない
  public void mouseClicked(MouseEvent e){}// 今回は使わないが、無いとコンパイルエラー
  public void mouseEntered(MouseEvent e){}// 今回は使わないが、無いとコンパイルエラー
  public void mouseExited(MouseEvent e){} // 今回は使わないが、無いとコンパイルエラー
  public void mousePressed(MouseEvent e){ // マウスボタンが押された時の処理
    switch (mode){
    case 1: // mode が１の場合，次の内容を実行する
    case 7:
      x = e.getX();
      y = e.getY();
      break;
    case 2: // mode が２もしくは
    case 3: // ３もしくは
    case 4: // ４の場合，次の内容を実行する
    case 5:
    case 6:
      px = e.getX();
      py = e.getY();
      
    }
  }
  public void mouseReleased(MouseEvent e){ // マウスボタンが離された時の処理
    switch (mode){
    case 2: // mode が２もしくは
    case 3: // ３もしくは
    case 4: // ４の場合，次の内容を実行する
    case 5:
    case 6:
      x = e.getX();
      y = e.getY();
      ow = x-px;
      oh = y-py;

      repaint(); // 再描画
    }
  }

  // ■ メソッド
  // 下記のマウス関連のメソッドは，MouseMotionListener をインターフェースとして実装しているため
  // 例え使わなくても必ず実装しなければならない
  public void mouseDragged(MouseEvent e){ // マウスがドラッグされた時の処理
    switch (mode){
    case 1: // mode が１の場合，次の内容を実行する
    case 7:
    x = e.getX();
    y = e.getY();
    px = x;
    py = y;
    repaint(); // 再描画
    }

  }
  public void mouseMoved(MouseEvent e){} // 今回は使わないが、無いとコンパイルエラー
}
}

