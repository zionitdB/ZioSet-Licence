package com.ZioSet.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Repo.ProductPortalRepo;
import com.ZioSet.Repo.PublisherPortalRepo;
import com.ZioSet.Repo.ReleasePortalRepo;
import com.ZioSet.dto.ReleasePortalDTO;
import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.Licence;
import com.ZioSet.model.Permissions;
import com.ZioSet.model.ProductPortal;
import com.ZioSet.model.PublisherPortal;
import com.ZioSet.model.ReleasePortal;
import com.ZioSet.model.RolePermission;
import com.ZioSet.model.Software;

@RestController
@CrossOrigin("*")
@RequestMapping("/portal")
public class PortalController {
	@Autowired
	PublisherPortalRepo publisherPortalRepo;
	@Autowired
	ProductPortalRepo productPortalRepo;
	
	@Autowired
	ReleasePortalRepo releasePortalRepo;

	
	
	
	@RequestMapping(value = "/addReleases", method = RequestMethod.POST)
	public @ResponseBody void addReleases(@RequestBody List<ReleasePortalDTO> releasePortals) {
		try {
			
			for(ReleasePortalDTO portal:releasePortals){
				
				System.out.println("Product"+portal.getProduct().getProductName());
				System.out.println("Publisher  "+portal.getProduct().getPublisher().getPublisherName());
				System.out.println("Edition  "+portal.getEdition());
				System.out.println("Edition  "+portal.getReleaseDate());
				ProductPortal portal2= new ProductPortal();
				PublisherPortal publisherPortal= new PublisherPortal();

				Optional<ProductPortal>  optionalproduct=productPortalRepo.getByName(portal.getProduct().getProductName());
				System.out.println("ProductPortal  "+optionalproduct.isPresent());
				Optional<PublisherPortal> optionalpubisher1=publisherPortalRepo.getPublisherByName(portal.getProduct().getPublisher().getPublisherName());
				System.out.println("Publisher  "+optionalpubisher1.isPresent());


				if(optionalproduct.isPresent()){
					portal2=optionalproduct.get();
				}else{

					ProductPortal portal3= new ProductPortal();
					portal3.setProductName(portal.getProduct().getProductName());
					Optional<PublisherPortal> optionalpubisher=publisherPortalRepo.getPublisherByName(portal.getProduct().getPublisher().getPublisherName());

					if(optionalpubisher.isPresent()){
						publisherPortal=optionalpubisher.get();
					}else{
						PublisherPortal portal4= new PublisherPortal();
						portal4.setPublisherName(portal.getProduct().getPublisher().getPublisherName());
						publisherPortal=publisherPortalRepo.save(portal4);
					}
					
					portal3.setPublisher(publisherPortal);
					portal2=productPortalRepo.save(portal3);
				
				}
				
				ReleasePortal portal4= new ReleasePortal();
				portal4.setEdition(portal.getEdition());
				portal4.setExtendedSupportEndDate(portal.getExtendedSupportEndDate());
				portal4.setPremiumSupportEndDate(portal.getPremiumSupportEndDate());
				portal4.setProduct(portal2);
				portal4.setPublisher(optionalpubisher1.get());
				portal4.setReleaseDate(portal.getReleaseDate());
				portal4.setRetirementDate(portal.getRetirementDate());
				releasePortalRepo.save(portal4);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	@GetMapping("/getPublishers")
	public @ResponseBody List<PublisherPortal> getPublishers() {
		List<PublisherPortal> list= new  ArrayList<PublisherPortal>();
		try {
			list= publisherPortalRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	@GetMapping("/getProducts")
	public @ResponseBody List<ProductPortal> getProductPortal() {
		List<ProductPortal> list= new  ArrayList<ProductPortal>();
		try {
			list= productPortalRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@GetMapping("/getProductsByPublisher")
	public @ResponseBody List<ProductPortal> getProductsByPublisher(@RequestParam("publisherId") int publisherId) {
		List<ProductPortal> list= new  ArrayList<ProductPortal>();
		try {
			list= productPortalRepo.getProductsByPublisher(publisherId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getReleasePortalPagination", method = RequestMethod.GET)
	public @ResponseBody List<ReleasePortal> getReleasePortalPagination(@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage,@RequestParam("productId") int productId) {
		List<ReleasePortal> list= new  ArrayList<ReleasePortal>();
		try {	
			
				list=releasePortalRepo.getReleasePortalPagination(pageNo,perPage,productId);
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getReleasePortalPaginationAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<ReleasePortal> getReleasePortalPaginationAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage,@RequestParam("productId") int productId) {
		List<ReleasePortal> list= new  ArrayList<ReleasePortal>();
		try {	
			
			list=releasePortalRepo.getReleasePortalPaginationAndSearch(searchText,pageNo,perPage,productId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getCountReleasePortal", method = RequestMethod.GET)
	public @ResponseBody int  getCountReleasePortal(@RequestParam("productId") int productId) {
		int  count= 0;
		try {
			count= releasePortalRepo.getCountReleasePortal(productId);

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getCountReleasePortalAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getCountReleasePortalAndSearch(@RequestParam("searchText") String searchText,@RequestParam("productId") int productId) {
		int  count= 0;
		try {
			count= releasePortalRepo.getCountReleasePortalAndSearch(searchText,productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/callLinke", method = RequestMethod.GET)
	public @ResponseBody List<ReleasePortal> callLinke() {
		List<ReleasePortal> list= new  ArrayList<ReleasePortal>();
		
		
		 try {
	            // Define the URL of the GET API you want to call
	            String apiUrl = "http://20.219.1.165:8092/release/getAllReleases";

	            // Create a URL object
	            URL url = new URL(apiUrl);

	            // Open a connection to the URL
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	            // Set the request method to GET
	            connection.setRequestMethod("GET");

	            // Get the response code
	            int responseCode = connection.getResponseCode();

	            // Check if the request was successful (status code 200)
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                // Read the response data
	                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                StringBuilder response = new StringBuilder();
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    response.append(line);
	                }
	                reader.close();

	                // Print the API response
	                System.out.println("API response data:");
	                System.out.println(response.toString());
	            } else {
	                // Handle any other status codes
	                System.out.println("Failed to fetch data. Status code: " + responseCode);
	            }

	            // Close the connection
	            connection.disconnect();
	        } catch (Exception e) {
	            // Handle exceptions that may occur during the request
	            e.printStackTrace();
	        }
		
		
		
		
		return list;
	}

}
