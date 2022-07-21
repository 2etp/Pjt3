package com.diet.biz.dietProgram.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diet.biz.dietProgram.Criteria;
import com.diet.biz.dietProgram.DietProgramService;
import com.diet.biz.dietProgram.FoodVO;
import com.diet.biz.dietProgram.KcalVO;

@Service
public class DietProgramServiceImpl implements DietProgramService{
	@Autowired
	private DietProgramDAOSpring dietProgramDAO;

	@Override
	public double dietStep1(KcalVO vo) {
		return dietProgramDAO.dietStep1(vo);
	}
	
	public int dietStep2(KcalVO vo) {
		return dietProgramDAO.dietStep2(vo);
	}
	
	public List<Integer> dietStep3(KcalVO vo) {
		return dietProgramDAO.dietStep3(vo);
	}
	
	public List<FoodVO> dietStep4(FoodVO vo, Criteria cri) {
		return dietProgramDAO.dietStep4(vo, cri);
	}
	
	public int getTotalFood(FoodVO vo) {
		return dietProgramDAO.getTotalFood(vo);
	}


}
