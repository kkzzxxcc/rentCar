
package com.oracle.rent.ch23.main;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Locale;

import javax.swing.*;

import com.oracle.rent.ch23.car.controller.CarController;
import com.oracle.rent.ch23.car.controller.CarControllerImpl;
import com.oracle.rent.ch23.car.window.RegCarDialog;
import com.oracle.rent.ch23.car.window.SearchCarDialog;
import com.oracle.rent.ch23.common.base.AbstractBaseWindow;
import com.oracle.rent.ch23.member.controller.MemberController;
import com.oracle.rent.ch23.member.controller.MemberControllerImpl;
import com.oracle.rent.ch23.member.exception.MemberException;
import com.oracle.rent.ch23.member.window.RegMemDialog;
import com.oracle.rent.ch23.member.window.SearchMemDialog;
import com.oracle.rent.ch23.res.controller.ResController;
import com.oracle.rent.ch23.res.controller.ResControllerImpl;
import com.oracle.rent.ch23.res.window.RegResDialog;
import com.oracle.rent.ch23.res.window.SearchResDialog;


// 렌트카 프로그램의 메인 창 클래스
public class RentMainWindow extends AbstractBaseWindow {
	// GUI 구성 요소 선언
	JFrame frame; // 프레임
	JMenuBar menuBar; // 메뉴바
	JMenu carMenu, memberMenu, resMenu, helpMenu, revenueMenu; // 메인 메뉴 항목
	// 서브 메뉴 항목
	JMenuItem carMenu11, carMenu12, carMenu13, carMenu14;
	JMenuItem memMenu21, memMenu22, memMenu23, memMenu24;
	JMenuItem resMenu31, resMenu32, resMenu33, resMenu34;
	JMenuItem helpMenu41;
	JPanel jPanel; // 패널
	JLabel lCarName; // 라벨
	JTextField tf; // 텍스트 필드
	JButton btnSearch; // 버튼
	JComboBox comboBox;  // 콤보 박스 (검색 조건)
	JMenuItem revenueMenuItem; // 매출 조회 버튼

	// 컨트롤러 선언
	MemberController memberController;
	CarController carController;
	ResController resController;

	// RentMainWindow 생성자
	public RentMainWindow() {
		// JFrame 객체 생성 및 설정
		frame = new JFrame("렌트카 조회/예약 시스템");
		menuBar = new JMenuBar();

		// 메인 메뉴 항목 객체 생성
		carMenu = new JMenu("차량 관리");
		memberMenu = new JMenu("회원관리");
		resMenu = new JMenu("예약관리");
		helpMenu = new JMenu("도움말");
		revenueMenu = new JMenu("매출");
	}

	// 서브메뉴 생성 메소드
	protected void startFrame() throws MemberException, IOException {
		// 프레임에 메뉴바를 추가
		frame.setJMenuBar(menuBar);
		// 메뉴바에 "회원관리" 항목을 추가
		menuBar.add(memberMenu);

		// 회원 관리 메뉴 관련 서브 메뉴 항목 추가
		memberMenu.add(memMenu21 = new JMenuItem("회원등록"));
		memberMenu.add(memMenu22 = new JMenuItem("회원조회"));
		memberMenu.addSeparator(); // 분리선 추가
		memberMenu.add(memMenu23 = new JMenuItem("회원수정"));
		memberMenu.add(memMenu24 = new JMenuItem("회원삭제"));

		// 메뉴바에 "차량 관리" 항목을 추가
		menuBar.add(carMenu);
		// 차량 관리 메뉴 관련 서브 메뉴 항목 추가
		carMenu.add(carMenu11 = new JMenuItem("차량등록"));
		carMenu.add(carMenu12 = new JMenuItem("차량조회"));
		carMenu.addSeparator(); // 분리선 추가
		carMenu.add(carMenu13 = new JMenuItem("차량수정"));
		carMenu.add(carMenu14 = new JMenuItem("차량삭제"));

		// 메뉴바에 "예약 관리" 항목을 추가
		menuBar.add(resMenu);
		// 예약 관리 메뉴 관련 서브 메뉴 항목 추가
		resMenu.add(resMenu31 = new JMenuItem("예약등록"));
		resMenu.add(resMenu32 = new JMenuItem("예약조회"));
		resMenu.addSeparator(); // 분리선 추가
		resMenu.add(resMenu33 = new JMenuItem("예약수정"));
		resMenu.add(resMenu34 = new JMenuItem("예약취소"));

		// 도움말 메뉴 항목 추가
		menuBar.add(helpMenu);
		helpMenu.add(helpMenu41 = new JMenuItem("버전"));

		// 패널과 그 구성 요소들 생성 및 설정
		jPanel = new JPanel();
		lCarName = new JLabel("차번호");
		tf = new JTextField(10);
		comboBox = new JComboBox();
		comboBox.addItem("차번호");
		comboBox.addItem("차이름");
		comboBox.addItem("차색상");
		comboBox.addItem("배기량");
		comboBox.addItem("제조사");

		btnSearch = new JButton("차량 조회하기");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SearchCarDialog(carController, "차 정보 조회");
			}
		});

		// 매출 메뉴 항목 추가
		menuBar.add(revenueMenu);
		revenueMenu.add(revenueMenuItem = new JMenuItem("총 매출 조회"));
		JMenuItem revenueMenuPeriod;
		revenueMenu.add(revenueMenuPeriod = new JMenuItem("기간별 매출 조회"));
		revenueMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTotalRevenue();
			}
		});

		// 기간별 매출 조회 이벤트 핸들러
		revenueMenuPeriod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPeriodRevenue();
			}
		});



		// 패널에 구성 요소들 추가
		jPanel.add(lCarName);
		jPanel.add(tf);
		jPanel.add(comboBox);
		jPanel.add(btnSearch);

		// 프레임의 콘텐츠 패인 설정
		Container con = frame.getContentPane();
		con.add(jPanel, "North");

		// 프레임의 위치와 크기 설정
		frame.setLocation(200, 100);
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 메뉴 항목 선택 이벤트와 이벤트 리스너를 연결
		carMenu11.addActionListener(new CarHandler());
		carMenu12.addActionListener(new CarHandler());
		carMenu13.addActionListener(new CarHandler());
		carMenu14.addActionListener(new CarHandler());

		memMenu21.addActionListener(new MemberHandler());
		memMenu22.addActionListener(new MemberHandler());
		memMenu23.addActionListener(new MemberHandler());
		memMenu24.addActionListener(new MemberHandler());

		resMenu31.addActionListener(new ResHandler());
		resMenu32.addActionListener(new ResHandler());
		resMenu33.addActionListener(new ResHandler());
		resMenu34.addActionListener(new ResHandler());

		helpMenu41.addActionListener(new HelpHandler());

		// 컨트롤러 생성
		memberController = new MemberControllerImpl();
		carController = new CarControllerImpl();
		resController = new ResControllerImpl();
	}


	// 매출 정보를 표시하는 메서드
	private void showTotalRevenue() {
		double totalRevenue = resController.getTotalRevenue();
		JOptionPane.showMessageDialog(this, "총 매출 : " + totalRevenue, "매출 조회", JOptionPane.INFORMATION_MESSAGE);
	}

	// 기간별 매출 조회 메소드
	private void showPeriodRevenue() {
		// 기간 입력 다이얼로그 표시
		String period = JOptionPane.showInputDialog(this, "조회할 기간을 입력하세요(YYYY-MM-DD ~ YYYY-MM-DD)", "기간별 매출 조회", JOptionPane.QUESTION_MESSAGE);
		if (period != null && !period.isEmpty()) {
			try {
				// 입력받은 기간을 이용하여 매출 조회
				double periodRevenue = resController.getPeriodRevenue(period);
				// 결과 출력
				JOptionPane.showMessageDialog(this, period + " 동안의 매출 : " + periodRevenue, "기간별 매출 조회", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "기간별 매출 조회에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		}
	}



	// 회원 관리 메뉴 항목의 이벤트 핸들러 클래스
	class MemberHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println(e.getActionCommand());

				if (e.getSource() == memMenu21) {
					new RegMemDialog(memberController, "회원 등록창");
				} else if (e.getSource() == memMenu22) {
					new SearchMemDialog(memberController, "회원 조회창");
				} else if (e.getSource() == memMenu23) {
					// 수정 대화상자 호출
					new SearchMemDialog(memberController, "회원 수정창");
				} else if (e.getSource() == memMenu24) {
					new SearchMemDialog(memberController, "회원 삭제창");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	// 차량 관리 메뉴 항목의 이벤트 핸들러 클래스
	class CarHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			if (e.getSource() == carMenu11) {
				new RegCarDialog(carController, "차량 등록창");
			} else if (e.getSource() == carMenu12) {
				new SearchCarDialog(carController, "차량 조회창");
			} else if (e.getSource() == carMenu13) {
				new SearchCarDialog(carController, "차량 수정창");
			}
			else if (e.getSource() == carMenu14) {
				new SearchCarDialog(carController, "차량 삭제창");
			}

		}
	}

	// 예약 관리 메뉴 항목의 이벤트 핸들러 클래스
	class ResHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == resMenu31) {
				new RegResDialog(resController, "예약 등록창");
			} else if (e.getSource() == resMenu32) {
				new SearchResDialog(resController, "예약 조회창");
			} else if (e.getSource() == resMenu33) {
				new SearchResDialog(resController, "예약 수정창");
			}
			else if (e.getSource() == resMenu34) {
				new SearchResDialog(resController, "예약 삭제창");
			}
		}
	}

	// 도움말 메뉴 항목의 이벤트 핸들러 클래스
	class HelpHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			version();
		}
	}

	// 버전 메뉴 클릭 시 수행하는 메소드
	public void version() {
		// 버전관리창 생성
		final JDialog d = new JDialog(this, "버전관리");
		JLabel jbver = new JLabel("       버전1.0");
		JLabel jbdate = new JLabel("       2024.06.14");
		JLabel jbauthor = new JLabel("       제작 : 이동근");

		d.setLayout(new FlowLayout());
		d.add(jbver);
		d.add(jbdate);
		d.add(jbauthor);

		d.setLocation(250, 230);
		d.setSize(200, 100);
		d.setVisible(true);

		// 버전관리창 종료 이벤트 핸들러
		d.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				d.dispose();
				d.setVisible(false);
			}
		});
	}

	// 메인 메소드
	public static void main(String[] args) {
		RentMainWindow test = new RentMainWindow();
		try {
			test.startFrame();
		} catch (MemberException | IOException e) {
			e.printStackTrace();
		}
	}
}




