package com.oracle.rent.ch23.member.window;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.oracle.rent.ch23.common.RentTableModel;
import com.oracle.rent.ch23.member.controller.MemberController;
import com.oracle.rent.ch23.member.vo.MemberVO;

public class ModifyMemDialog extends JDialog{
    JPanel jPanel;
    JLabel lId,lName,lPassword,lAddress,lPhoneNum;
    JTextField tfId,tfName,tfPassword,tfAddress,tfPhoneNum ;
    JButton btnReg;

    MemberController memberController;
    String[][] memItems = new String[0][5];;
    JTable rentTable;
    RentTableModel rentTableModel;
    String[] columnNames = { "아이디", "비밀번호", "이름", "주소", "전화번호" };

    Object[][] memData = null; // 테이블에 표시될 회원 정보 저장 2차원 배열
    int rowIdx = 0, colIdx = 0; // 테이블 수정 시 선택한 행과 열 인덱스 저장

    String memid;

    public ModifyMemDialog(MemberController memberController, String memid) {
        this.memberController = memberController;
        this.memid = memid;
        setTitle("회원 수정");
        init();
    }

    private void init() {
        lId = new JLabel("아이디");
        lPassword = new JLabel("비밀번호");
        lName= new JLabel("이름");
        lAddress = new JLabel("주소");
        lPhoneNum = new JLabel("전화번호");


        tfId=new JTextField(memid);
        tfId.setEditable(false);
        tfPassword=new JTextField(20);
        tfName=new JTextField(20);
        tfAddress=new JTextField(20);
        tfPhoneNum=new JTextField(20);

        btnReg=new JButton("회원수정하기");

        btnReg.addActionListener(e -> {

            String password=tfPassword.getText().trim();
            String  name=tfName.getText().trim();
            String address=tfAddress.getText().trim();
            String phoneNum=tfPhoneNum.getText().trim();

            MemberVO memVO=new MemberVO(memid, password, name, address, phoneNum);

            memberController.modMember(memVO);

            showMessage("회원을 수정했습니다.");

            dispose();

        });


        jPanel=new JPanel(new GridLayout(0,2));
        jPanel.add(lId);
        jPanel.add(tfId);

        jPanel.add(lPassword);
        jPanel.add(tfPassword);

        jPanel.add(lName);
        jPanel.add(tfName);

        jPanel.add(lAddress);
        jPanel.add(tfAddress);

        jPanel.add(lPhoneNum);
        jPanel.add(tfPhoneNum);

        add(jPanel,BorderLayout.NORTH);
        add(btnReg,BorderLayout.SOUTH);

        setLocation(400, 200);
        setSize(400,400);
        setModal(true); //항상 부모창 위에 보이게 합니다.
        setVisible(true);
    }

    private void showMessage(String msg){
        JOptionPane.showMessageDialog(this,
                msg,
                "메지지박스",
                JOptionPane.INFORMATION_MESSAGE);
    }


}
