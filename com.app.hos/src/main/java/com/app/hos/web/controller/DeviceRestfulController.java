package com.app.hos.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.service.api.SystemFacade;
import com.app.hos.share.utils.DateTime;

@Controller
public class DeviceRestfulController {
	
	@Autowired
	private SystemFacade systemFacade;
	
	@RequestMapping(value = "/devices/statuses/{serial}", method=RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public ResponseEntity<List<DeviceStatus>> getDeviceStatuses(@PathVariable(value="serial") String serial,
									@RequestParam("from") long from,
									@RequestParam("to") long to) {
		List<DeviceStatus> statuses = systemFacade.getDeviceStatuses(serial, new DateTime(from), new DateTime(to));
		return new ResponseEntity<List<DeviceStatus>>(statuses,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/devices/remove/{serial}", method=RequestMethod.GET,produces = "application/json")
	public ResponseEntity<?> removeDevice(@PathVariable(value="serial") String serial) {
		ResponseEntity<?> response;
		if (systemFacade.removeDevice(serial)) {
			response = ResponseEntity.status(HttpStatus.OK).body("Device remove succesfully.");
		} else {
			response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Cannot remove device.");
		}
		return response;
	}
}
