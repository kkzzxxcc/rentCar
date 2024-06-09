package com.oracle.rent.ch23.car.window;

import com.oracle.rent.ch23.car.controller.CarController;
import com.oracle.rent.ch23.car.vo.CarVO;

import javax.swing.*;
import java.awt.*;

public class ModifyCarDialog extends JDialog {
    JLabel lCarNum, lCarName, lSize, lColor, lMaker;
    JTextField tfCarNum, tfCarName, tfSize, tfColor, tfMaker;
    JButton btnModify;

    CarController carController;
    String carNum;

    public ModifyCarDialog(CarController carController, String carNum) {
        this.carController = carController;
        this.carNum = carNum;
        setTitle("차량 수정");
        init();
    }

    private void init() {
        lCarNum = new JLabel("차량번호");
        lCarName = new JLabel("차량명");
        lSize = new JLabel("배기량");
        lColor = new JLabel("차색상");
        lMaker = new JLabel("차제조사");

        tfCarNum = new JTextField(carNum);
        tfCarNum.setEditable(false); // 차량번호는 수정 불가능하도록 설정
        tfCarName = new JTextField(20);
        tfSize = new JTextField(20);
        tfColor = new JTextField(20);
        tfMaker = new JTextField(20);

        btnModify = new JButton("수정하기");
        btnModify.addActionListener(e -> {
            String carName = tfCarName.getText().trim();
            int carSize = Integer.parseInt(tfSize.getText().trim());
            String carColor = tfColor.getText().trim();
            String carMaker = tfMaker.getText().trim();



            CarVO carVO = new CarVO(carNum, carName, carColor, carSize, carMaker);
            carController.modCarInfo(carVO);
            showMessage("차량 정보를 수정했습니다.");
            dispose();
        });

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(lCarNum);
        panel.add(tfCarNum);
        panel.add(lCarName);
        panel.add(tfCarName);
        panel.add(lSize);
        panel.add(tfSize);
        panel.add(lColor);
        panel.add(tfColor);
        panel.add(lMaker);
        panel.add(tfMaker);

        add(panel, BorderLayout.CENTER);
        add(btnModify, BorderLayout.SOUTH);

        setLocation(400, 200);
        setSize(400, 300);
        setModal(true);
        setVisible(true);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지 박스", JOptionPane.INFORMATION_MESSAGE);
    }
}
