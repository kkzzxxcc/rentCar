package com.oracle.rent.ch23.res.window;

import com.oracle.rent.ch23.res.controller.ResController;
import com.oracle.rent.ch23.res.vo.ResVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyResDialog extends JDialog {
    JPanel jPanel;
    JLabel lResNum, lResCarNum, lResDate, lUseBeginDate, lReturnDate, lResUserId, lPrice, lStatus;
    JTextField tfResNum, tfResCarNum, tfResDate, tfUseBeginDate, tfReturnDate, tfResUserId, tfPrice, tfStatus;
    JButton btnResModify;

    ResController resController;
    ResVO resVO;

    public ModifyResDialog(ResController resController, ResVO resVO, String title) {
        this.resController = resController;
        this.resVO = resVO;
        setTitle(title);
        init();
    }

    private void init() {
        lResNum = new JLabel("예약번호");
        lResCarNum = new JLabel("예약차번호");
        lResDate = new JLabel("예약날짜");
        lUseBeginDate = new JLabel("렌터카사용시작일자");
        lReturnDate = new JLabel("렌터카반납일자");
        lResUserId = new JLabel("예약자아이디");
        lPrice = new JLabel("가격");
        lStatus = new JLabel("상태");

        tfResNum = new JTextField(resVO.getResNumber(), 20);
        tfResCarNum = new JTextField(resVO.getResCarNumber(), 20);
        tfResDate = new JTextField(resVO.getResDate(), 20);
        tfUseBeginDate = new JTextField(resVO.getUseBeginDate(), 20);
        tfReturnDate = new JTextField(resVO.getReturnDate(), 20);
        tfResUserId = new JTextField(resVO.getResUserId(), 20);
        tfPrice = new JTextField(Double.toString(resVO.getPrice()), 20); // 가격 입력 상자 추가 및 기존 가격 설정
        tfStatus = new JTextField(resVO.getStatus(), 20); // 상태 입력 상자 추가 및 기존 상태 설정

        btnResModify = new JButton("수정하기");
        btnResModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resNum = tfResNum.getText().trim();
                String resCarNum = tfResCarNum.getText().trim();
                String resDate = tfResDate.getText().trim();
                String useBeginDate = tfUseBeginDate.getText().trim();
                String returnDate = tfReturnDate.getText().trim();
                String resUserId = tfResUserId.getText().trim();
                double price = Double.parseDouble(tfPrice.getText().trim()); // 입력된 가격 값 가져오기
                String status = tfStatus.getText().trim(); // 입력된 상태 값 가져오기

                ResVO newResVO = new ResVO(resNum, resCarNum, resDate, useBeginDate, returnDate, resUserId, price, status); // 수정된 ResVO 객체 생성
                resController.modResInfo(newResVO);

                showMessage("예약을 수정했습니다.");
                dispose();
            }
        });

        jPanel = new JPanel(new GridLayout(0, 2));
        jPanel.add(lResNum);
        jPanel.add(tfResNum);
        jPanel.add(lResCarNum);
        jPanel.add(tfResCarNum);
        jPanel.add(lResDate);
        jPanel.add(tfResDate);
        jPanel.add(lUseBeginDate);
        jPanel.add(tfUseBeginDate);
        jPanel.add(lReturnDate);
        jPanel.add(tfReturnDate);
        jPanel.add(lResUserId);
        jPanel.add(tfResUserId);
        jPanel.add(lPrice);
        jPanel.add(tfPrice); // 가격 입력 상자 추가
        jPanel.add(lStatus);
        jPanel.add(tfStatus); // 상태 입력 상자 추가

        add(jPanel, BorderLayout.NORTH);
        add(btnResModify, BorderLayout.SOUTH);

        setLocation(400, 200);
        setSize(400, 500); // 크기 조정
        setModal(true);
        setVisible(true);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지 박스", JOptionPane.INFORMATION_MESSAGE);
    }
}
