package com.example.RestaurantManagement.service;

import com.example.RestaurantManagement.dto.TableRequestDto;
import com.example.RestaurantManagement.dto.TableResponseDto;
import com.example.RestaurantManagement.entity.DineTable;
import com.example.RestaurantManagement.entity.RestaurantBranch;
import com.example.RestaurantManagement.enums.TableStatus;
import com.example.RestaurantManagement.exception.BadRequestException;
import com.example.RestaurantManagement.exception.ResourceNotFoundException;
import com.example.RestaurantManagement.repository.BranchRepository;
import com.example.RestaurantManagement.repository.DineTableRepository;
import com.example.RestaurantManagement.repository.RestaurantRepository;
import com.example.RestaurantManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//Manager cretate a table
public class TableService {
    private UserRepository userRepository;
    private DineTableRepository tableRepository;
    private BranchRepository branchRepository;

//manager will create the table
public TableResponseDto create(TableRequestDto dto,String ManagerEmail){
  RestaurantBranch branch=branchRepository.findById(dto.getBranchId())
          .orElseThrow(()->new ResourceNotFoundException())
          validateManager(managerEmail,branch);
if(tableRepository.existsByBranchIdAndTableNumber(dto.getBranchId(),dto.getTableNumber())) {
    throw new BadRequestException(() ->"Table number is aalredy existed in this branch"))
}
DineTable table=DineTable.builder()
        .branch(branch)
        .tableNumber(dto.getTableNumber())
        .capacity(dto.getCapacity())
        .status(TableStatus.AVAILABLE)
        .build();
return toDto(tableRepository.save(table));
}

@Transactional
public void delete(Long id, String managerEmail) {
    DineTable table = tableRepository.findById(id)
            .orElseThrow(() -> "this tbale not founded")
    validateManger(managerEmail, table.getBranch());
    if (table.getStatus() == TableStatus.OCCUPIED) {

        throw new BadRequestException("Occupies table cannt be deleted");
    }
    tableRepository.delete(table);
}
  //view tabes can be viewd by superadmin owner and manger
   public List <TableResponseDto> getByBranch(Long branchId,String requesterEmail){
 branchRepository.findById(branchId)
         .orElseThrow(()->"Branch not found")
validateViewAccess(requesterEmail,branchId)

return tableRepository.findByBranchId(branchId)
        .stream().map(this :: toDto).collect(Collectors.toList());
    }

    //view single tabale
    public TableResponseDto getById(Long id,String requesterEmail){
 DineTable table= tableRepository.findById(id)
         .orElseThrow(()-> "Table wit this id not found")

validateViewAccess(requesterEmail,table.getBranch().getId());
return toDto(table);
    }


//lofic for validate manger
    public TableResponseDto validateManager()




}



}
