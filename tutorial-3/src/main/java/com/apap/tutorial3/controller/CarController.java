package com.apap.tutorial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add (@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "brand", required = true) String brand,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "price", required = true) Long price,
			@RequestParam(value = "amount", required = true) Integer amount) {
		
		CarModel car = new CarModel(id,brand,type,price,amount);
		mobilService.addCar(car);
		return "add";
	}
	
	@RequestMapping("/car/view")
	public String view(@RequestParam("id") String id, Model model) {
		CarModel archive = mobilService.getCarDetail(id);
		model.addAttribute("car",archive);
		return "view-car";
	}
	
	@RequestMapping("/car/view/{id}")
	public String viewid(@PathVariable String id,Model model) {
		String hasil = "";
		List <CarModel> temp = mobilService.getCarList();
		if (id == null) {
			hasil  +="belum di Input";
			model.addAttribute("id", hasil);
			return "error";
		}else {
			for (CarModel car : temp) {
				if(car.getId().equalsIgnoreCase(id)) {
					model.addAttribute("listcar",temp);
					return "viewall-car";
				}
			}
			hasil+="tidak diketemukan";
			model.addAttribute("id", hasil);
			return "error";
		}
	}
	
	@RequestMapping("/car/update/{id}/amount/{amount}")
	public String update(@PathVariable String id , @PathVariable String amount, Model model ) {
		String tulis = "";
		List <CarModel> temp1 = mobilService.getCarList();
		int amount1 = Integer.parseInt(amount);
		if (id == null) {
			tulis  +="belum di Input";
			model.addAttribute("id", tulis);
			return "error";
		}else{
			for (CarModel car : temp1) {
				if (car.getId().equalsIgnoreCase(id)) {
					car.setAmount(amount1);
					tulis+="Data Telah Berhasil Diubah";
					model.addAttribute("sukses", tulis);
					return "sukses";
				}
			}
			tulis+="ID tidak diketemukan";
			model.addAttribute("id", tulis);
			return "error";
		}
	}
	
	@RequestMapping("/car/delete/{id}")
	public String delete(@PathVariable String id, Model model) {
		String hasil = "";
		List <CarModel> temp1 = mobilService.getCarList();
		if (id == null) {
			hasil += "belum di input";
			model.addAttribute("id", hasil);
			return "error";
		}else {
			for (CarModel car : temp1) {
				if (car.getId().equalsIgnoreCase(id)) {
					temp1.remove(car);
					hasil += "Data berhasil dihapus";
					model.addAttribute("sukses", hasil);
					return "sukses";
				}
			}
			hasil+="tidak diketemukan";
			model.addAttribute("id", hasil);
			return "error";
		}
		
	}
	
	@RequestMapping("/car/viewall")
	public String viewall(Model model) {
		List<CarModel> archive = mobilService.getCarList();
		model.addAttribute("listcar", archive);
		return "viewall-car";
	}
	
}
