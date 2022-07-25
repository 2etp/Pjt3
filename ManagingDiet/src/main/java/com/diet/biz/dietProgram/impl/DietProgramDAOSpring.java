package com.diet.biz.dietProgram.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.diet.biz.dietProgram.Criteria;
import com.diet.biz.dietProgram.FoodVO;
import com.diet.biz.dietProgram.KcalVO;
import com.diet.biz.dietProgram.UserDietVO;

@Repository
public class DietProgramDAOSpring {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// SQL 명령어들
	private final String FOOD_SELECT = "select * from tblfood where `탄수화물(g)` < ? and `단백질(g)` < ? and `지방(g)` < ? limit ?, ?";
	private final String FOOD_TOT_COUNT = "select count(*) from tblfood where `탄수화물(g)` < ? and `단백질(g)` < ? and `지방(g)` < ?";
	private final String FOOD_INSERT = "insert into tbldiet(id, food, regdate) values(?, ?, now())";
	private final String DIET_UPDATE = "update tbldiet set food = ?, regdate = now() where id = ?";
	private final String DIET_USER_CHECK = "select id from tbldiet where id = ?";
	
	// 사용자 스펙을 통한 기초대사량 계산
	public double dietStep1(KcalVO vo) {
		
		double bmr = 0;
		
		// 해리스 베네딕트 방정식 이용
		if(vo.getSex().equals("남")) {
			bmr = 66 + (13.7 * vo.getWeight() + (5 * vo.getHeight()) - (6.8 * vo.getAge()));
			System.out.println("남자용 방적식 결과");
			
		} else if(vo.getSex().equals("여")) {
			bmr = 655 + (9.6 * vo.getWeight() + (1.7 * vo.getHeight()) - (4.7 * vo.getAge()));
			System.out.println("여자용 방적식 결과");
		}
		
		return bmr;
	}
	
	// 기초대사량에 근거한 칼로리 계산
	public int dietStep2(KcalVO vo) {
		
		int kcal = 0;
		
		if(vo.getType().equals("diet")) {
			
			switch(vo.getActivityAmount()) {
				case "few":
					kcal = (int) Math.round(vo.getBmr() * 1.2 * 0.8);
					break;
				case "aFew":
					kcal = (int) Math.round(vo.getBmr() * 1.375 * 0.8);
					break;
				case "normal":
					kcal = (int) Math.round(vo.getBmr() * 1.55 * 0.8);
					break;
				case "quite":
					kcal = (int) Math.round(vo.getBmr() * 1.725 * 0.8);
					break;
				case "aLot":
					kcal = (int) Math.round(vo.getBmr() * 1.9 * 0.8);
					break;
				}
			
		} else if(vo.getType().equals("bulkup")) {
			
			switch(vo.getActivityAmount()) {
				case "few":
					kcal = (int) Math.round(vo.getBmr() * 1.2 * 1.2);
					break;
				case "aFew":
					kcal = (int) Math.round(vo.getBmr() * 1.375 * 1.2);
					break;
				case "normal":
					kcal = (int) Math.round(vo.getBmr() * 1.55 * 1.2);
					break;
				case "quite":
					kcal = (int) Math.round(vo.getBmr() * 1.725 * 1.2);
					break;
				case "aLot":
					kcal = (int) Math.round(vo.getBmr() * 1.9 * 1.2);
					break;
				}
		}
		
		return kcal;				
	}
	
	// 영양소대로 칼로리 구성(g으로 환산)
	public List<Integer> dietStep3(KcalVO vo) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		int carbs = 0;
		int protein = 0;
		int fat = 0;
		
		carbs = (int)Math.round((vo.getKcal() * 0.5)/4);
		protein = (int)Math.round((vo.getKcal() * 0.3)/4);
		fat = (int)Math.round((vo.getKcal() * 0.2)/9);
		
		list.add(carbs);
		list.add(protein);
		list.add(fat);
		
		return list;
	}
	
	// 칼로리에 맞는 음식 리스트 추천
	public List<FoodVO> dietStep4(KcalVO vo, Criteria cri) {
		System.out.println("dietStep4(DAO) 발동!!!");
		Object[] args = { vo.getCarbs(), vo.getProtein(), vo.getFat(), cri.getSkip(), cri.getAmount() };
		return jdbcTemplate.query(FOOD_SELECT, args, new DietProgramRowMapper());
	}
	
	// 음식 총 개수
	public int getTotalFood(KcalVO vo) {
		System.out.println("getTotalFood 발동!!!");
		Object[] args = { vo.getCarbs(), vo.getProtein(), vo.getFat() };
		return jdbcTemplate.queryForObject(FOOD_TOT_COUNT, args, Integer.class);
	}
	
	// 유저가 선택한 음식 리스트 DB에 넣기
	public void insertFood(UserDietVO vo) {
		System.out.println("insertFood 발동!!!");
		jdbcTemplate.update(FOOD_INSERT, vo.getId(), vo.getFood());
	}
	
	// 유저가 선택한 음식 리스트 수정하기
	public void updateFood(UserDietVO vo) {
		System.out.println("updateFood 발동!!!");
		jdbcTemplate.update(DIET_UPDATE, vo.getFood(), vo.getId());
	}
	
	// tbldiet의 유저 정보 유무 확인하기
	public String getDietUser(UserDietVO vo) {
		try {
			System.out.println("getDietUser 발동!!!");
			Object[] args = {vo.getId()};
			System.out.println("jdbc 템플릿 쿼리문 발동");
			String user = jdbcTemplate.queryForObject(DIET_USER_CHECK, args, String.class);
			System.out.println("DAOSpring : " + user);
			return user;
			
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}
}
