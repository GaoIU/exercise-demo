package com.gaoc.user.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaoc.core.model.R;
import com.gaoc.user.model.CommonMeta;
import com.gaoc.user.service.ICommonMetaService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/commonMeta")
@RequiredArgsConstructor
@RestController
public class CommonMetaController {

	private final ICommonMetaService commonMetaService;

	@PostMapping
	public R save(@RequestBody @Valid CommonMeta commonMate) {
		boolean save = commonMetaService.save(commonMate);
		if (save) {
			return R.ok();
		}

		return R.fail("操作失败");
	}

}
