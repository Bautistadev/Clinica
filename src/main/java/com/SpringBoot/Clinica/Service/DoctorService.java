package com.SpringBoot.Clinica.Service;

import com.SpringBoot.Clinica.Entity.DoctorEntity;
import com.SpringBoot.Clinica.Repository.DoctorRepository;
import com.SpringBoot.Clinica.Service.Mapper.DoctorMapper;
import com.SpringBoot.Clinica.model.DoctorDTO;
import com.SpringBoot.Clinica.model.DoctorRequestDTO;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    private DoctorMapper doctorMapper;


    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper){
        this.doctorMapper =  doctorMapper;
        this.doctorRepository =  doctorRepository;
    }



    /**
     * @operation: save
     * @param: DoctorRequestDTO
     * @return: DoctorRequest
     * */
    public DoctorDTO save(DoctorRequestDTO doctorRequestDTO) throws Exception {
        DoctorEntity doctorEntity = this.doctorMapper.map(doctorRequestDTO);
        DoctorDTO response = this.doctorMapper.map(this.doctorRepository.save(doctorEntity));
        return response;
    }





    /**
     * @operation: find by id
     * @param: Integer
     * @return: DoctorDTO
     * */
    public DoctorDTO findById(Integer id) throws Exception {
        DoctorDTO response = this.doctorMapper.map(this.doctorRepository.findById(id).get());
        return response;
    }






    /**
     * @operation: exists by id
     * @param: Integer
     * @return: Boolean
     * */
    public boolean existsById(Integer id){
        if(id == null|| id == 0)
            throw new IllegalArgumentException();

        return this.doctorRepository.existsById(id);
    }






    /**
     * @operation: find all doctor
     * @return: List --> DoctorDTO
     * */
    public List<DoctorDTO> findAll(){

        List<DoctorDTO> response = new ArrayList<>();

        this.doctorRepository.findAll().forEach(e ->{
            try {
                response.add(this.doctorMapper.map(e));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return response;
    }






    /**
     * @operation: count all Doctors
     * @return: Number
     * */

    public Long count(){
        return this.doctorRepository.count();
    }




    /**
     * @operation: delete by DoctorDTO id
     * @param: Integer
     * @return: -
     * */
    public void deleteById(Integer id){
        if(id == null || id == 0)
            throw new IllegalArgumentException("El id no puede ser nulo o 0");

        this.doctorRepository.deleteById(id);
    }








    /**
     * @operation: delete by DoctorDTO object
     * @param: DoctorEntity
     * @return: -
     * */
    public void delete(DoctorDTO doctorDTO){

        if(doctorDTO == null)
            throw new IllegalArgumentException("El objecto no puede ser nulo");


        DoctorEntity doctorEntity = this.doctorMapper.map(doctorDTO);
        this.doctorRepository.delete(doctorEntity);
    }





}
