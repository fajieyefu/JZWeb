package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class ContractServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String buyer_name=req.getParameter("buyer_name");
		 buyer_name = new String(buyer_name.getBytes("ISO-8859-1"), "UTF-8");
         String sign_date=req.getParameter("sign_date");
         sign_date = new String(sign_date.getBytes("ISO-8859-1"), "UTF-8");
         String sign_address=req.getParameter("sign_address");
         sign_address = new String(sign_address.getBytes("ISO-8859-1"), "UTF-8");
         String tractor=req.getParameter("tractor");
         tractor = new String(tractor.getBytes("ISO-8859-1"), "UTF-8");
         String power_type=req.getParameter("power_type");
         power_type = new String(power_type.getBytes("ISO-8859-1"), "UTF-8");
         String traction_pin=req.getParameter("traction_pin");
         traction_pin = new String(traction_pin.getBytes("ISO-8859-1"), "UTF-8");
         String traction_seat=req.getParameter("traction_seat");
         traction_seat = new String(traction_seat.getBytes("ISO-8859-1"), "UTF-8");
         String heightfromground=req.getParameter("heightfromground");
         heightfromground = new String(heightfromground.getBytes("ISO-8859-1"), "UTF-8");
         String vehicle_type=req.getParameter("vehicle_type");
         vehicle_type = new String(vehicle_type.getBytes("ISO-8859-1"), "UTF-8");
         String type_name=req.getParameter("type_name");
         type_name = new String(type_name.getBytes("ISO-8859-1"), "UTF-8");
         String plan=req.getParameter("plan");
         plan = new String(plan.getBytes("ISO-8859-1"), "UTF-8");
         String length_out=req.getParameter("length_out");
         length_out = new String(length_out.getBytes("ISO-8859-1"), "UTF-8");
         String measure_length_out=req.getParameter("measure_length_out");
         measure_length_out = new String(measure_length_out.getBytes("ISO-8859-1"), "UTF-8");
         String width_out=req.getParameter("width_out");
         width_out = new String(width_out.getBytes("ISO-8859-1"), "UTF-8");
         String measure_width_out=req.getParameter("measure_width_out");
         measure_width_out = new String(measure_width_out.getBytes("ISO-8859-1"), "UTF-8");
         String height_out=req.getParameter("height_out");
         height_out = new String(height_out.getBytes("ISO-8859-1"), "UTF-8");
         String measure_height_out=req.getParameter("measure_height_out");
         measure_height_out = new String(measure_height_out.getBytes("ISO-8859-1"), "UTF-8");
         String length_in=req.getParameter("length_in");
         length_in = new String(length_in.getBytes("ISO-8859-1"), "UTF-8");
         String measure_length_in=req.getParameter("measure_length_in");
         measure_length_in = new String(measure_length_in.getBytes("ISO-8859-1"), "UTF-8");
         String width_in=req.getParameter("width_in");
         width_in = new String(width_in.getBytes("ISO-8859-1"), "UTF-8");
         String measure_width_in=req.getParameter("measure_width_in");
         measure_width_in = new String(measure_width_in.getBytes("ISO-8859-1"), "UTF-8");
         String height_in=req.getParameter("height_in");
         height_in = new String(height_in.getBytes("ISO-8859-1"), "UTF-8");
         String measure_height_in=req.getParameter("measure_height_in");
         measure_height_in = new String(measure_height_in.getBytes("ISO-8859-1"), "UTF-8");
         String upper_wing_plate=req.getParameter("upper_wing_plate");
         upper_wing_plate = new String(upper_wing_plate.getBytes("ISO-8859-1"), "UTF-8");
         String lower_wing_plate=req.getParameter("lower_wing_plate");
         lower_wing_plate = new String(lower_wing_plate.getBytes("ISO-8859-1"), "UTF-8");
         String vertical_plate=req.getParameter("vertical_plate");
         vertical_plate = new String(vertical_plate.getBytes("ISO-8859-1"), "UTF-8");
         String stringer_height=req.getParameter("stringer_height");
         stringer_height = new String(stringer_height.getBytes("ISO-8859-1"), "UTF-8");
         String lower_bending=req.getParameter("lower_bending");
         lower_bending = new String(lower_bending.getBytes("ISO-8859-1"), "UTF-8");
         String punch=req.getParameter("punch");
         punch = new String(punch.getBytes("ISO-8859-1"), "UTF-8");
         String box_bottom_thickness=req.getParameter("box_bottom_thickness");
         box_bottom_thickness = new String(box_bottom_thickness.getBytes("ISO-8859-1"), "UTF-8");
         String box_bottom_style=req.getParameter("box_bottom_style");
         box_bottom_style = new String(box_bottom_style.getBytes("ISO-8859-1"), "UTF-8");
         String waterproof_tank=req.getParameter("waterproof_tank");
         waterproof_tank = new String(waterproof_tank.getBytes("ISO-8859-1"), "UTF-8");
         String middle_beam_material=req.getParameter("middle_beam_material");
         middle_beam_material = new String(middle_beam_material.getBytes("ISO-8859-1"), "UTF-8");
         String middle_beam_Style=req.getParameter("middle_beam_Style");
         middle_beam_Style = new String(middle_beam_Style.getBytes("ISO-8859-1"), "UTF-8");
         String middle_beam_num=req.getParameter("middle_beam_num");
         middle_beam_num = new String(middle_beam_num.getBytes("ISO-8859-1"), "UTF-8");
         String middle_beam_punch=req.getParameter("middle_beam_punch");
         middle_beam_punch = new String(middle_beam_punch.getBytes("ISO-8859-1"), "UTF-8");
         String side_beam_material=req.getParameter("side_beam_material");
         side_beam_material = new String(side_beam_material.getBytes("ISO-8859-1"), "UTF-8");
         String xiangban_style=req.getParameter("xiangban_style");
         xiangban_style = new String(xiangban_style.getBytes("ISO-8859-1"), "UTF-8");
         String xiangban_side=req.getParameter("xiangban_side");
         xiangban_side = new String(xiangban_side.getBytes("ISO-8859-1"), "UTF-8");
         String xiangban_num=req.getParameter("xiangban_num");
         xiangban_num = new String(xiangban_num.getBytes("ISO-8859-1"), "UTF-8");
         String box_size=req.getParameter("box_size");
         box_size = new String(box_size.getBytes("ISO-8859-1"), "UTF-8");
         String box_style=req.getParameter("box_style");
         box_style = new String(box_style.getBytes("ISO-8859-1"), "UTF-8");
         String hualan_side=req.getParameter("hualan_side");
         hualan_side = new String(hualan_side.getBytes("ISO-8859-1"), "UTF-8");
         String hualan_side_num=req.getParameter("hualan_side_num");
         hualan_side_num = new String(hualan_side_num.getBytes("ISO-8859-1"), "UTF-8");
         String tube_style=req.getParameter("tube_style");
         tube_style = new String(tube_style.getBytes("ISO-8859-1"), "UTF-8");
         String zhanzhu_style=req.getParameter("zhanzhu_style");
         zhanzhu_style = new String(zhanzhu_style.getBytes("ISO-8859-1"), "UTF-8");
         String suogan=req.getParameter("suogan");
         suogan = new String(suogan.getBytes("ISO-8859-1"), "UTF-8");
         String backdoor_style=req.getParameter("backdoor_style");
         backdoor_style = new String(backdoor_style.getBytes("ISO-8859-1"), "UTF-8");
         String booster=req.getParameter("booster");
         booster = new String(booster.getBytes("ISO-8859-1"), "UTF-8");
         String longmenjia_style=req.getParameter("longmenjia_style");
         longmenjia_style = new String(longmenjia_style.getBytes("ISO-8859-1"), "UTF-8");
         String pengbukuang_num=req.getParameter("pengbukuang_num");
         pengbukuang_num = new String(pengbukuang_num.getBytes("ISO-8859-1"), "UTF-8");
         String pengbu_size=req.getParameter("pengbu_size");
         pengbu_size = new String(pengbu_size.getBytes("ISO-8859-1"), "UTF-8");
         String ladder=req.getParameter("ladder");
         ladder = new String(ladder.getBytes("ISO-8859-1"), "UTF-8");
         String ba_tai=req.getParameter("ba_tai");
         ba_tai = new String(ba_tai.getBytes("ISO-8859-1"), "UTF-8");
         String la_cheng=req.getParameter("la_cheng");
         la_cheng = new String(la_cheng.getBytes("ISO-8859-1"), "UTF-8");
         String peng_gan_num=req.getParameter("peng_gan_num");
         peng_gan_num = new String(peng_gan_num.getBytes("ISO-8859-1"), "UTF-8");
         String anzhuang_style=req.getParameter("anzhuang_style");
         anzhuang_style = new String(anzhuang_style.getBytes("ISO-8859-1"), "UTF-8");
         String kit_left=req.getParameter("kit_left");
         kit_left = new String(kit_left.getBytes("ISO-8859-1"), "UTF-8");
         String kit_right=req.getParameter("kit_right");
         kit_right = new String(kit_right.getBytes("ISO-8859-1"), "UTF-8");
         String rope_device_num=req.getParameter("rope_device_num");
         rope_device_num = new String(rope_device_num.getBytes("ISO-8859-1"), "UTF-8");
         String rope_device_position=req.getParameter("rope_device_position");
         rope_device_position = new String(rope_device_position.getBytes("ISO-8859-1"), "UTF-8");
         String spare_tire_num=req.getParameter("spare_tire_num");
         spare_tire_num = new String(spare_tire_num.getBytes("ISO-8859-1"), "UTF-8");
         String spare_tire_position=req.getParameter("spare_tire_position");
         spare_tire_position = new String(spare_tire_position.getBytes("ISO-8859-1"), "UTF-8");
         String elevator_num=req.getParameter("elevator_num");
         elevator_num = new String(elevator_num.getBytes("ISO-8859-1"), "UTF-8");
         String water_bag=req.getParameter("water_bag");
         water_bag = new String(water_bag.getBytes("ISO-8859-1"), "UTF-8");
         String water_bag_space=req.getParameter("water_bag_space");
         water_bag_space = new String(water_bag_space.getBytes("ISO-8859-1"), "UTF-8");
         String axle=req.getParameter("axle");
         axle = new String(axle.getBytes("ISO-8859-1"), "UTF-8");
         String steel_plate=req.getParameter("steel_plate");
         steel_plate = new String(steel_plate.getBytes("ISO-8859-1"), "UTF-8");
         String wheelbase=req.getParameter("wheelbase");
         wheelbase = new String(wheelbase.getBytes("ISO-8859-1"), "UTF-8");
         String brake_air_chamber=req.getParameter("brake_air_chamber");
         brake_air_chamber = new String(brake_air_chamber.getBytes("ISO-8859-1"), "UTF-8");
         String tyre=req.getParameter("tyre");
         tyre = new String(tyre.getBytes("ISO-8859-1"), "UTF-8");
         String steel_ring=req.getParameter("steel_ring");
         steel_ring = new String(steel_ring.getBytes("ISO-8859-1"), "UTF-8");
         String abs=req.getParameter("abs");
         abs = new String(abs.getBytes("ISO-8859-1"), "UTF-8");
         String body_color_up=req.getParameter("body_color_up");
         body_color_up = new String(body_color_up.getBytes("ISO-8859-1"), "UTF-8");
         String body_color_bottom=req.getParameter("body_color_bottom");
         body_color_bottom = new String(body_color_bottom.getBytes("ISO-8859-1"), "UTF-8");
         String suspension=req.getParameter("suspension");
         suspension = new String(suspension.getBytes("ISO-8859-1"), "UTF-8");
         String another_quest=req.getParameter("another_quest");
         another_quest = new String(another_quest.getBytes("ISO-8859-1"), "UTF-8");
         String model=req.getParameter("model");
         model = new String(model.getBytes("ISO-8859-1"), "UTF-8");
         String tonnage=req.getParameter("tonnage");
         tonnage = new String(tonnage.getBytes("ISO-8859-1"), "UTF-8");
         String plate_num=req.getParameter("plate_num");
         plate_num = new String(plate_num.getBytes("ISO-8859-1"), "UTF-8");
         String axis_number=req.getParameter("axis_number");
         axis_number = new String(axis_number.getBytes("ISO-8859-1"), "UTF-8");
         String color=req.getParameter("color");
         color = new String(color.getBytes("ISO-8859-1"), "UTF-8");
         String steel=req.getParameter("steel");
         steel = new String(steel.getBytes("ISO-8859-1"), "UTF-8");
         String num=req.getParameter("num");
         num = new String(num.getBytes("ISO-8859-1"), "UTF-8");
         String price=req.getParameter("price");
         price = new String(price.getBytes("ISO-8859-1"), "UTF-8");
         String total_amount=req.getParameter("total_amount");
         total_amount = new String(total_amount.getBytes("ISO-8859-1"), "UTF-8");
         String shoufu=req.getParameter("shoufu");
         shoufu = new String(shoufu.getBytes("ISO-8859-1"), "UTF-8");
         String extra_days=req.getParameter("extra_days");
         extra_days = new String(extra_days.getBytes("ISO-8859-1"), "UTF-8");
         String delivery_mode=req.getParameter("delivery_mode");
         delivery_mode = new String(delivery_mode.getBytes("ISO-8859-1"), "UTF-8");
         String user_man=req.getParameter("account");
         user_man = new String(user_man.getBytes("ISO-8859-1"), "UTF-8");
         String user_man_name=req.getParameter("user_man_name");
         user_man_name = new String(user_man_name.getBytes("ISO-8859-1"), "UTF-8");
         Map<String,String> map = new HashMap<String, String>();
         map.put("user_man",user_man);
         map.put("user_man_name",user_man_name);
         map.put("buyer_name",buyer_name);
         map.put("sign_date",sign_date);
         map.put("sign_address",sign_address);
         map.put("tractor",tractor);
         map.put("power_type",power_type);
         map.put("traction_pin",traction_pin);
         map.put("traction_seat",traction_seat);
         map.put("heightfromground",heightfromground);
         map.put("vehicle_type",vehicle_type);
         map.put("type_name",type_name);
         map.put("length_out",length_out);
         map.put("measure_length_out",measure_length_out);
         map.put("width_out",width_out);
         map.put("measure_width_out",measure_width_out);
         map.put("height_out",height_out);
         map.put("measure_height_out",measure_height_out);
         map.put("length_in",length_in);
         map.put("measure_length_in",measure_length_in);
         map.put("width_in",width_in);
         map.put("measure_width_in",measure_width_in);
         map.put("height_in",height_in);
         map.put("measure_height_in",measure_height_in);
         map.put("upper_wing_plate",upper_wing_plate);
         map.put("lower_wing_plate",lower_wing_plate);
         map.put("vertical_plate",vertical_plate);
         map.put("stringer_height",stringer_height);
         map.put("lower_bending",lower_bending);
         map.put("punch",punch);
         map.put("box_bottom_thickness",box_bottom_thickness);
         map.put("box_bottom_style",box_bottom_style);
         map.put("waterproof_tank",waterproof_tank);
         map.put("middle_beam_material",middle_beam_material);
         map.put("middle_beam_Style",middle_beam_Style);
         map.put("middle_beam_num",middle_beam_num);
         map.put("middle_beam_punch",middle_beam_punch);
         map.put("side_beam_material",side_beam_material);
         map.put("xiangban_style",xiangban_style);
         map.put("xiangban_side",xiangban_side);
         map.put("xiangban_num",xiangban_num);
         map.put("box_size",box_size);
         map.put("box_style",box_style);
         map.put("hualan_side",hualan_side);
         map.put("hualan_side_num",hualan_side_num);
         map.put("tube_style",tube_style);
         map.put("zhanzhu_style",zhanzhu_style);
         map.put("suogan",suogan);
         map.put("backdoor_style",backdoor_style);
         map.put("booster",booster);
         map.put("longmenjia_style",longmenjia_style);
         map.put("pengbukuang_num",pengbukuang_num);
         map.put("pengbu_size",pengbu_size);
         map.put("ladder",ladder);
         map.put("ba_tai",ba_tai);
         map.put("la_cheng",la_cheng);
         map.put("peng_gan_num",peng_gan_num);
         map.put("anzhuang_style",anzhuang_style);
         map.put("kit_left",kit_left);
         map.put("kit_right",kit_right);
         map.put("rope_device_num",rope_device_num);
         map.put("rope_device_position",rope_device_position);
         map.put("spare_tire_num",spare_tire_num);
         map.put("spare_tire_position",spare_tire_position);
         map.put("elevator_num",elevator_num);
         map.put("water_bag",water_bag);
         map.put("water_bag_space",water_bag_space);
         map.put("axle",axle);
         map.put("steel_plate",steel_plate);
         map.put("wheelbase",wheelbase);
         map.put("brake_air_chamber",brake_air_chamber);
         map.put("tyre",tyre);
         map.put("steel_ring",steel_ring);
         map.put("abs",abs);
         map.put("body_color_up",body_color_up);
         map.put("body_color_bottom",body_color_bottom);
         map.put("suspension",suspension);
         map.put("another_quest",another_quest);
         map.put("model",model);
         map.put("tonnage",tonnage);
         map.put("plate_num",plate_num);
         map.put("axis_number",axis_number);
         map.put("color",color);
         map.put("steel",steel);
         map.put("num",num);
         map.put("price",price);
         map.put("total_amount",total_amount);
         map.put("shoufu",shoufu);
         map.put("extra_days",extra_days);
         map.put("delivery_mode",delivery_mode);
         Service serv = new Service();
         String info = serv.makeContractInput(map);
         resp.setCharacterEncoding("UTF-8");
         resp.setContentType("text/html");
         PrintWriter out = resp.getWriter();
         out.print(info);
         out.flush();
         out.close();
	}

}