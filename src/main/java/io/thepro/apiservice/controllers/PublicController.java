package io.thepro.apiservice.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@PreAuthorize("#oauth2.hasAuthority('SOME_ROLE')")
@RequestMapping("/public")
public class PublicController {

	@GetMapping("/test/")
	@ResponseBody
	public ResponseEntity<?> findById(@PathVariable String string) {
		return
				new ResponseEntity<>(string, HttpStatus.OK);
	}


	@GetMapping("/data")
	public Map<String, Object> getData() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "Hello there");
		return response;
	}

}
