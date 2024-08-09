package org.jsp.reservation_api.controller;

import java.time.LocalDate;
import java.util.List;

import org.jsp.reservation_api.dto.BusRequest;
import org.jsp.reservation_api.dto.ResponceStructure;
import org.jsp.reservation_api.model.Bus;
import org.jsp.reservation_api.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/buses")
public class BusController {
	@Autowired
	private BusService busService;

	@PostMapping("/{admin_id}")
	public ResponseEntity<ResponceStructure<Bus>> saveBus(@RequestBody BusRequest busRequest,
			@PathVariable int admin_id) {
		return busService.saveBus(busRequest, admin_id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponceStructure<Bus>> updateBus(@RequestBody BusRequest busRequest, @PathVariable int id) {
		return busService.updateBus(busRequest, id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponceStructure<Bus>> findById(@PathVariable int id) {
		return busService.findById(id);
	}

	@GetMapping
	public ResponseEntity<ResponceStructure<List<Bus>>> findAll() {
		return busService.findAll();
	}

	@GetMapping("/find")
	public ResponseEntity<ResponceStructure<List<Bus>>> findBuses(@RequestParam String from, @RequestParam String to,
			@RequestParam LocalDate dateOfDeparture) {
		return busService.findBuses(from, to, dateOfDeparture);
	}

	@GetMapping("/find/{admin_id}")
	public ResponseEntity<ResponceStructure<List<Bus>>> findByAdminId(@PathVariable int admin_id) {
		return busService.findByAdminId(admin_id);
	}
	@DeleteMapping("{id}")
    public ResponseEntity<ResponceStructure<String>> deleteBus(@PathVariable int id) {
    	return busService.delete(id);
    			
    }
}
