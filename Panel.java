package com.pychego.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

// 画布
public class Panel extends JPanel implements KeyListener, ActionListener {

    int length;  // 蛇的长度
    int[] snakeX = new int[100];  // 存放蛇每段身体的位置
    int[] snakeY = new int[100];
    String direction;  // 蛇的方向
    boolean isStart = false;    // 游戏是否开始
    boolean isFail = false;  // 是否失败
    int foodx;   // 定义食物位置
    int foody;
    int score;
    Random random = new Random();
    Timer timer = new Timer(100, this); // 定时器

    public Panel() {
        init();

        this.setFocusable(true); // 获取键盘焦点在游戏界面
        this.addKeyListener(this);
        timer.start(); // 定时器开始工作
    }

    public void init() {
        length = 3;
        snakeX[0] = 100; snakeY[0] = 100;  // 头部坐标
        snakeX[1] = 75; snakeY[1] = 100;  // 身体
        snakeX[2] = 50; snakeY[2] = 100;
        direction = "right";
        score=0;

        foodx = 25 + 25 * random.nextInt(34);
        foody  = 75 + 25 * random.nextInt(24);

    }

    // Graphics 画笔
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 清屏
        this.setBackground(Color.WHITE);
       // Data.header.paintIcon(this, g, 25, 11); // 绘制头部图片
        g.fillRect(25, 75, 850, 600);

        switch (direction) {
            case "right":
                Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "left":
                Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "up":
                Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "down":
                Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
        }

        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]); // 这是增加身体
        }

        Data.food.paintIcon(this, g, foodx, foody ); // 添加食物图片

        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD,18));
        g.drawString("长度： " + length, 750,50);  // 在右上角写长度
        g.drawString("分数： "+score,750,28);

        if (isStart == false) {
            // 游戏提示
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD,40));
            g.drawString("按下空格开始游戏", 300,300);

        }
        if (isFail == true) {
            // 游戏提示
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD,40));
            g.drawString("游戏结束，按下空格重新开始", 300,300);

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 键盘按下弹起事件
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 键盘按下事件
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if(isFail) {
                isFail = false;
                init();
            } else {
                isStart = ! isStart;
            }
            repaint();
        }
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                direction = "left"; break;
            case KeyEvent.VK_RIGHT:
                direction = "right"; break;
            case KeyEvent.VK_UP:
                direction = "up"; break;
            case KeyEvent.VK_DOWN:
                direction = "down"; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //  键盘弹起事件
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // 执行定时操作
        if (isStart && !isFail) {
            for (int i = length-1; i > 0 ; i--) {
                // 必须要从最后一节开始移动
                snakeX[i] = snakeX[i-1]; // 除了脑袋，身体都移动。
                snakeY[i] = snakeY[i-1];
            }
            switch (direction) {
                case "right":
                    snakeX[0] += 25;
                    if (snakeX[0] > 850) {snakeX[0] = 25;}
                    break;
                case "left":
                    snakeX[0]-= 25;
                    if (snakeX[0] < 25) {snakeX[0] = 850;}
                    break;
                case "up":
                    snakeY[0] -= 25;
                    if (snakeY[0] < 75) {snakeY[0] = 600;}
                    break;
                case "down":
                    snakeY[0] += 25;
                    if (snakeY[0] > 650) {snakeY[0] = 75;}
                    break;
            }

            // 吃到食物
            if (snakeX[0] == foodx & snakeY[0] == foody) {
                length++;score++;
                foodx = 25 + 25 * random.nextInt(34);
                foody  = 75 + 25 * random.nextInt(24);

            }
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] & snakeY[0] == snakeY[i]){
                    isFail = true;
                }
            }
            repaint();
        }
        timer.start();
    }
}
