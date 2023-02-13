package com.poly.rest.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poly.entity.Product;
import com.poly.entity.ProductPhoto;
import com.poly.service.ProductPhotoService;
//duy
@CrossOrigin("*")
@RestController
public class ProductPhotoRestController {
	
	@Autowired
	ProductPhotoService photoService;
	
	@PostMapping("/rest/product_photo/{images}/{idP}")
	public List<String> saveImage(@PathVariable("images") String imagesFolder,
							@PathParam("files") MultipartFile[] files, @PathVariable("idP") Integer idProduct) {
		System.out.println("Luu anh " + files);
		File dir = new File(System.getProperty("user.dir")+"/target/classes/static/assets/admin/"+imagesFolder);
		 if (!dir.exists()) {
			dir.mkdirs();
		}
		 
		 List<String> filenames = new ArrayList<String>();
		 for (MultipartFile fileImage : files) {
			String name = System.currentTimeMillis() + fileImage.getOriginalFilename();
			String fileName = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
			
			try {
				 Path path = Paths.get(dir.getAbsolutePath(), fileName);
				 fileImage.transferTo(path);
				 
				 ProductPhoto productPhoto = new ProductPhoto();
				 productPhoto.setId(fileName);
				 Product p = new Product();
				 p.setId(idProduct);
				 productPhoto.setProduct(p);
				 photoService.save(productPhoto);
				 
				 filenames.add(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filenames;
	}
	
	@GetMapping("/rest/ProductPhotoByProId/{idP}")
	public List<String> getListProductByProdId(@PathVariable("idP") Integer idProduct) {
		return photoService.getListIdProductPhotoByProductId(idProduct);
	}
	
	@DeleteMapping("/rest/deleteProductPhoto/{name}")
	public void deleteProductPhoto(@PathVariable("name") String id) {
		photoService.delete(id);
	}
	
}
