package com.ZioSet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ZioSet.Repo.BranchRepo;
import com.ZioSet.Repo.DepartmentRepo;
import com.ZioSet.Service.EmployeeServices;
import com.ZioSet.dto.Status;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.Branch;
import com.ZioSet.model.Department;
import com.ZioSet.model.Employee;

@RestController
@CrossOrigin("*")
@RequestMapping("employee")
public class EmployeeController {
	
	
	@Autowired
	EmployeeServices employeeServices;
	@Autowired
	BranchRepo branchRepo;
	@Autowired
	DepartmentRepo departmentRepo;
	
	
	
	
	@RequestMapping(value = "/addNewEmployee", method = RequestMethod.POST)
	public @ResponseBody Status addNewEmployee(@RequestBody Employee employee) {
		Status status =new Status();
		try {
			employeeServices.addNewEmployee(employee);
			status.setCode(200);
			status.setMessage("Employee Added Successfully ");
			
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
	public @ResponseBody Status updateEmployee(@RequestBody Employee employee) {
		Status status =new Status();
		try {
			Optional<Employee> optional=employeeServices.getEmployeeByNo(employee.getEmployeeNo());
			if(optional.get().getEmployeeId()==employee.getEmployeeId()){
				employeeServices.addNewEmployee(employee);
				status.setCode(200);
				status.setMessage("Employee Update Successfully ");
			}else{
				status.setCode(200);
				status.setMessage("Updated Employee No is already Exite");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	
	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
	public @ResponseBody Status deleteEmployee(@RequestBody Employee employee) {
		Status status =new Status();
		try {
			System.out.println("Delete Employee");
			List<AssetEmployeeAssigned> list = employeeServices.getEmployeeWiseAllocationReport(employee.getEmployeeId());
			
			if(list.size()!=0){
				status.setCode(500);
				status.setMessage("Employee has assinged asset ..so can not delete");
			}else{
				status.setCode(200);
				status.setMessage("Employee deleted ....succesfully");
				employeeServices.deleteEmployee(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	@RequestMapping(value = "/getAllEmployeeListByBranch", method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployeeListByBranch(@RequestParam("branchName") String branchName) {
		Status status =new Status();
		List<Employee> list =new ArrayList<Employee>();
		try {
			List<Employee> list1=employeeServices.getAllEmployeesByBranch(branchName);
			int srNo=1;
			for(Employee employee : list1){
					if(employee.getAddedBy()!=null){
					String addBy=employee.getAddedBy().getFirstName()+" "+employee.getAddedBy().getLastName();
					employee.setAddBy(addBy);
				}
				if(employee.getHireDate()!=null){
				//	String hrDate=dateFormat.format(employee.getHireDate());
				//	employee.setHrDate(hrDate);

				}
				System.out.println("ACTIVE "+employee.getActive());
				if(employee.getActive()==1){
					employee.setStatus("Active");
				}
				if(employee.getActive()==2){
					employee.setStatus("Paid Leave");				
				}
				if(employee.getActive()==3){
					employee.setStatus("Unpaid Leave");
				}
				if(employee.getActive()==4){
					employee.setStatus("Terminated");
				}
				employee.setSrNo(srNo);
				
				if(employee.getManager()!=null){
					employee.setManager(employee.getManager().replace(' ',' '));

				}
				list.add(employee);
				srNo++;
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return list;
	}
	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees() {
		Status status =new Status();
		List<Employee> list =new ArrayList<Employee>();
		try {
			List<Employee> list1=employeeServices.getAllEmployees();
			int srNo=1;
			for(Employee employee : list1){
					if(employee.getAddedBy()!=null){
					String addBy=employee.getAddedBy().getFirstName()+" "+employee.getAddedBy().getLastName();
					employee.setAddBy(addBy);
				}
				if(employee.getHireDate()!=null){
				//	String hrDate=dateFormat.format(employee.getHireDate());
				//	employee.setHrDate(hrDate);

				}
				System.out.println("ACTIVE "+employee.getActive());
				if(employee.getActive()==1){
					employee.setStatus("Active");
				}
				if(employee.getActive()==2){
					employee.setStatus("Paid Leave");				
				}
				if(employee.getActive()==3){
					employee.setStatus("Unpaid Leave");
				}
				if(employee.getActive()==4){
					employee.setStatus("Terminated");
				}
				employee.setSrNo(srNo);
				
				if(employee.getManager()!=null){
					employee.setManager(employee.getManager().replace(' ',' '));

				}
				list.add(employee);
				srNo++;
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return list;
	}
	@RequestMapping(value = "/getEmployeeByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<Employee> getEmployeeByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Employee> list= new  ArrayList<Employee>();
		try {	
			list=employeeServices.getEmployeeByLimit(page_no,item_per_page);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getEmployeeByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<Employee> getEmployeeByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Employee> list= new  ArrayList<Employee>();
		try {	
			
			list=employeeServices.getEmployeeByLimitAndSearch(searchText,pageNo,perPage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getEmployeeCount", method = RequestMethod.GET)
	public @ResponseBody int  getEmployeeCount() {
		int  count= 0;
		try {
			count= employeeServices.getEmployeeCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getEmployeeCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getEmployeeCountAndSearch(@RequestParam("searchText") String searchText,@RequestParam("type") String type) {
		int  count= 0;
		try {
			count= employeeServices.getEmployeeCountAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/uploadEmployee", method = RequestMethod.POST)
	public @ResponseBody Status uploadEmployee(ModelMap model, @ModelAttribute(value = "file") MultipartFile file,
			HttpServletRequest request,@RequestParam("uploadBy")String uploadBy) throws ParseException {
		Status  status= new Status();
		int uploadedCount=0;
		int totalCount=0;
		System.out.println("UPloading...............EMployee");
		System.out.println("Uploading Employee");
		String responceMsg="";
		try {
			if (!(file == null)) {
				if (file.isEmpty()) {
				} else {
					//System.out.println(file.getOriginalFilename());
					try {
						File dir = new File(System.getProperty("catalina.base"), "uploads");
						File uplaodedFile = new File(dir + file.getOriginalFilename());
						file.transferTo(uplaodedFile);
						FileInputStream excelFile = new FileInputStream(uplaodedFile);
						Workbook workbook = new XSSFWorkbook(excelFile);
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
						DataFormatter formatter = new DataFormatter();
						Sheet datatypeSheet = workbook.getSheetAt(0); 
						int i = 0;
						//Optional<UserInfo> optUser=userServices.findById(uploadBy);

						totalCount= datatypeSheet.getLastRowNum();
						while (i <= datatypeSheet.getLastRowNum()) { 
							if(totalCount==0){
								status.setCode(500);
								status.setMessage("Data Not Found in sheet");
								String responceMsg0="";

								responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
								responceMsg0+=" \r\n Upaloading Date :: "+new Date();
								
									responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
								
								responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
								
								responceMsg0+="\r\n Data Not Found in sheet";
								String newResMsg=responceMsg0+" \r\n "+responceMsg;
								status.setResmessage(newResMsg);
								
								return status; 
							}

							XSSFRow row = null;
							row = (XSSFRow) datatypeSheet.getRow(i++);
						
							String str=formatter.formatCellValue(row.getCell(0), evaluator);
							if(str.length()==0) {
								continue;
							}
							Employee employee= new Employee();
							int checkExcel=0;
							String EmployeeNo=formatter.formatCellValue(row.getCell(0), evaluator);
							String username=formatter.formatCellValue(row.getCell(1), evaluator);
							String fanme=formatter.formatCellValue(row.getCell(2), evaluator);
							String lanme=formatter.formatCellValue(row.getCell(3), evaluator);
							
							String empType=formatter.formatCellValue(row.getCell(5), evaluator);
							String bUnit=formatter.formatCellValue(row.getCell(6), evaluator);
							
							String depName=formatter.formatCellValue(row.getCell(8), evaluator);
							String team=formatter.formatCellValue(row.getCell(9), evaluator);
							String workLocation=formatter.formatCellValue(row.getCell(10), evaluator);
							
							String manager=formatter.formatCellValue(row.getCell(12), evaluator);
							
							
							 							
							String mobile=formatter.formatCellValue(row.getCell(22), evaluator);
							
							String email=formatter.formatCellValue(row.getCell(23), evaluator);
							String branch=formatter.formatCellValue(row.getCell(24), evaluator);
							String empstatus=formatter.formatCellValue(row.getCell(25), evaluator);

							String employeeName =fanme+" "+lanme;
							//String depName=row.getCell(8).toString();

							
							
							

							if(i==1){
								if(row.getCell(0).toString().equalsIgnoreCase("User/Employee ID")){
									checkExcel++;
								}							
								if(row.getCell(1).toString().equalsIgnoreCase("Username")){
									checkExcel++;
								}
								if(row.getCell(2).toString().equalsIgnoreCase("First Name")){
									checkExcel++;	
									
								}
								if(row.getCell(3).toString().equalsIgnoreCase("Last Name")){
									checkExcel++;	
								}
								if(row.getCell(8).toString().equalsIgnoreCase("Department")){
									checkExcel++;	
								}
								if(row.getCell(24).toString().equalsIgnoreCase("Location")){
									checkExcel++;	
								}
								
								
								//System.out.println("checkExcel "+checkExcel);
								if(	checkExcel==6){
								}else{
									
									status.setCode(500);
									status.setMessage("Wrong Sheet Selected");
									String responceMsg0="";
								//	Optional<UserInfo> optional=userServices.findById(uploadBy);

									responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
									responceMsg0+=" \r\n Upaloading Date :: "+new Date();

										responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
									
									responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
									
									responceMsg0+="\r\n Wrong Sheet Selected";
									String newResMsg=responceMsg0+" \r\n "+responceMsg;
									status.setResmessage(newResMsg);
									return status; 
								}
							}else{
								String hdate=formatter.formatCellValue(row.getCell(14), evaluator);
								/*//System.out.println("Hire date11 "+hdate);
								Date hireDate = null;
								if(hdate!=""||hdate!=null){
									hireDate  =new SimpleDateFormat("dd/MM/yyyy").parse(hdate);  

								}*/
								String str1 = row.getCell(0).toString();
								//System.out.println("NO DATA "+str1.length());
								if(str1.length()==0) {
									status.setCode(500);
									status.setMessage("Data Not Found in sheet");
									String responceMsg0="";
								//	Optional<UserInfo> optional=userServices.findById(uploadBy);

									responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
									responceMsg0+=" \r\n Upaloading Date :: "+new Date();
									
										responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
									
									responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
									
									responceMsg0+="\r\n Data Not Found in sheet";
									String newResMsg=responceMsg0+" \r\n "+responceMsg;
									status.setResmessage(newResMsg);
									
									return status; 
								}
								if(EmployeeNo==null||EmployeeNo==""){
									responceMsg+=" \r\n No Employee No Found  for Row No ::"+i;
								//	//System.out.println("No Tag No Found  for Row No ::"+i);
								}
								if(fanme==null||fanme==""){
									responceMsg+=" \r\n No Fisrt Name Found for Row No ::"+i;
									////System.out.println("No Hostname Found for Row No ::"+i);
								}
								if(lanme==null||lanme==""){
									responceMsg+=" \r\n No Last Name Found for Row No ::"+i;
									////System.out.println("No Hostname Found for Row No ::"+i);
								}
								if(username==null||username==""){
									responceMsg+=" \r\n No Username Found for Row No ::"+i;
									////System.out.println("No Hostname Found for Row No ::"+i);
								}
								
								Optional<Branch> bOptional= branchRepo.getBranchByName(branch);

								if(branch==null||branch==""){
									responceMsg+=" \r\n No Branch Name Found for Row No ::"+i;
									////System.out.println("No BranchName Found for Row No ::"+i);
								}else{
									if(! bOptional.isPresent()){
										responceMsg+=" \r\n InValid Branch Name for Row No ::"+i;
									System.out.println("InValid Branch Name for Row No ::"+i);
									}else{
										 employee.setBranch(bOptional.get());
									}
									
								}
								Optional<Department> optional = departmentRepo.getDepartmentByName(depName);
								Department department = new Department();
								if(depName==null||depName==""){
									responceMsg+=" \r\n No Department  Found for Row No ::"+i;
									////System.out.println("No BranchName Found for Row No ::"+i);
								}else{
									
									if(optional.isPresent()){
										department=optional.get();
									}else{
										
										int maxId=departmentRepo.getMaxDepartmentId();
										int newId=maxId++;
										System.out.println("MAX ID .............................................."+maxId);
										Department department1 = new Department();
										department1.setDepartmentName(depName);
										department1.setDepartmentId(newId);
										department1.setActive(1);
										department=departmentRepo.save(department1);
									}
									
									
									
								}
							

								
								
									
									Optional<Employee> optionalEmp = employeeServices.getEmployeeByNo(EmployeeNo);

									System.out.println("STatus "+empstatus);
									 if (optionalEmp.isPresent()) {
										employee= optionalEmp.get();
									}
									if(empstatus.equalsIgnoreCase("Active")){
										employee.setActive(1);
									}
									if(empstatus.equalsIgnoreCase("Paid Leave")){
										employee.setActive(2);
									}
									if(empstatus.equalsIgnoreCase("Unpaid Leave")){
										employee.setActive(3);
									}
									if(empstatus.equalsIgnoreCase("Terminated")){
										employee.setActive(4);
									}
									 
									 employee.setFirstName(fanme);
									 employee.setLastName(lanme);
									 employee.setEmployeeType(empType);
									 employee.setBussinessUnit(bUnit);
									 employee.setTeam(team);
									 employee.setWorkLocation(workLocation);
									 employee.setManager(manager);
									 //employee.setHireDate(hireDate);
									
									 employee.setUsername(username);
									// employee.setAddedDate(new Date());
									 employee.setEmail(email);
									 employee.setEmployeeName(employeeName);
									 employee.setEmployeeNo(EmployeeNo);
									 employee.setMobileNo(mobile);
									// employee.setAddedBy(optUser.get());
									/* boolean mobileVal=true;
									 boolean emailVal=true;
									 //System.out.println("Email  :: "+email);
									 //System.out.println("Mobile No :: "+mobile);*/
									/* if(mobile.length()!=10){
										 mobileVal=false;
										 responceMsg+=" \r\n InValid Mobile  for Row No ::"+i;
									 }else{
										 if(mobile.matches(".*[a-zA-Z]+.*")){
											 mobileVal=false;
											 responceMsg+=" \r\n InValid Mobile  for Row No ::"+i;
										 }else{
											 mobileVal=true;
										 }
										 
									 }*/
									/* if(email.indexOf("@")!=-1)
									 {
									    emailVal=true;
									 }
									 else
									 {
											responceMsg+=" \r\n InValid Email  for Row No ::"+i;

									    emailVal=false;
									 }
									 */
									 
									 
								
									 
									 if( EmployeeNo !="" && lanme!="" && fanme!="" && username!=""){
										 employee.setDepartment(department);
										employeeServices.addNewEmployee(employee);
										 uploadedCount++;
									 }
									
									
								
								
								
							}
							
							
							
							
						}

						
						workbook.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						
						
					}
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		status.setCode(200);
		status.setMessage("Upalod Successfully");
		String responceMsg0="";
		responceMsg0="Uploading...... ";
	//	Optional<UserInfo> optional=userServices.findById(uploadBy);

		responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
		responceMsg0+=" \r\n Upaloading Date :: "+new Date();
		
			responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
	
		responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
		if(uploadedCount==totalCount){
			responceMsg0+=" \r\n No Constrain Found ";
		}else{
			responceMsg0+=" \r\n Found Following Constrain";
		}
		
		String newResMsg=responceMsg0+" \r\n "+responceMsg;
		status.setResmessage(newResMsg);
		return status;
	}
}
