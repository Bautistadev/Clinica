package com.SpringBoot.Clinica.Controller;

import com.SpringBoot.Clinica.Service.DoctorService;
import com.SpringBoot.Clinica.api.DoctorsApiDelegate;
import com.SpringBoot.Clinica.model.DoctorDTO;
import com.SpringBoot.Clinica.model.DoctorListDTO;
import com.SpringBoot.Clinica.model.DoctorRequestDTO;
import com.SpringBoot.Clinica.model.RetriveAttributeDoctor200Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

public class DoctorController implements DoctorsApiDelegate {

    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return DoctorsApiDelegate.super.getRequest();
    }




    /**
     * @operation: save
     * @param: DoctorRequestDTO
     * @return: DoctorRequest
     * */
    @Override
    public ResponseEntity<DoctorDTO> doctorCreate(DoctorRequestDTO doctorRequestDTO) {

        if (doctorRequestDTO == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.doctorService.save(doctorRequestDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






    @Override
    public ResponseEntity<Void> removeDoctor(Integer doctorId) {

        if(doctorId == 0 || doctorId == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        this.doctorService.deleteById(doctorId);

        return ResponseEntity.ok().build();
    }




    /**
     * @operation: find all doctor
     * @return: List --> DoctorDTO
     * */
    @Override
    public ResponseEntity<DoctorListDTO> retriveAllDoctors() {


        DoctorListDTO doctorListDTO = new DoctorListDTO().items(this.doctorService.findAll());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctorListDTO);
    }







    @Override
    public ResponseEntity<RetriveAttributeDoctor200Response> retriveAttributeDoctor(Integer attribute, Integer value) {
        return DoctorsApiDelegate.super.retriveAttributeDoctor(attribute, value);
    }







    /**
     * @operation: find by id
     * @param: Integer
     * @return: DoctorDTO
     * */
    @Override
    public ResponseEntity<DoctorDTO> retriveDoctor(Integer doctorId) {

        if(doctorId == null || doctorId == 0)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.doctorService.findById(doctorId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }







    @Override
    public ResponseEntity<DoctorDTO> updateDoctor(Integer doctorId, DoctorDTO doctorDTO) {
        return DoctorsApiDelegate.super.updateDoctor(doctorId, doctorDTO);
    }
}
