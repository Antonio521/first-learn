package com.pychego.snake;

import javax.swing.*;

public class StartGames {
     public static void main(String[] args) {
          // 绘制静态窗口
          JFrame frame = new JFrame("贪吃蛇");
          frame.setBounds(10, 10, 900, 720);  // 设置窗口位置，大小
          frame.setResizable(false);   // 不可改变大小
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // 关闭事件

          frame.add(new Panel());
          frame.setVisible(true); // 可见


     }
}
