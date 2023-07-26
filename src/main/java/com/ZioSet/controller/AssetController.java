package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Service.AssetService;
import com.ZioSet.model.Asset;

@RestController
@CrossOrigin("*")
@RequestMapping("asset")
public class AssetController {
	
	@Autowired
	AssetService assetServices;
	
	@RequestMapping(value = "/getAssetsByLimit/{page_no}/{item_per_page}/{type}", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getAssetsByLimit(@PathVariable("page_no") int page_no,
			@PathVariable("item_per_page") int item_per_page) {
		List<Asset> list = new ArrayList<Asset>();
		try {

			
				list = assetServices.getAssetsByLimit(page_no, item_per_page);
			
			
			for (Asset asset : list) {
				int currentAge0;
				Date invoiceDate = asset.getInvoiceDate();
				Date today = new Date();
				if (invoiceDate != null) {
					long diff = today.getTime() - invoiceDate.getTime();
					int days = (int) (diff / 1000 / 60 / 60 / 24);
					int year = (days / 356);
					asset.setAge(String.valueOf(year));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/getAssetsByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getAssetsByLimitAndSearch(@RequestParam("searchText") String searchText,
			@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
		List<Asset> list = new ArrayList<Asset>();
		try {
			list = assetServices.getAssetsByLimitAndSearch(searchText, pageNo, perPage);

			for (Asset asset : list) {
				int currentAge0;
				Date invoiceDate = asset.getInvoiceDate();
				Date today = new Date();
				// System.out.println("Invoice Date "+invoiceDate+" for
				// "+asset.getId());
				// System.out.println("today "+today);
				if (invoiceDate != null) {
					long diff = today.getTime() - invoiceDate.getTime();
					int days = (int) (diff / 1000 / 60 / 60 / 24);
					int year = (days / 356);
					// System.out.println ("Days: " + year);
					asset.setAge(String.valueOf(year));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAssetCount", method = RequestMethod.GET)
	public @ResponseBody int getAssetCount() {
		int count = 0;
		try {
			count = assetServices.getAssetsCount();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@RequestMapping(value = "/getAssetCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int getAssetCountAndSearch(@RequestParam("searchText") String searchText,
			@RequestParam("type") String type) {
		int count = 0;
		try {
			count = assetServices.getAssetCountAndSearch(searchText);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


}
