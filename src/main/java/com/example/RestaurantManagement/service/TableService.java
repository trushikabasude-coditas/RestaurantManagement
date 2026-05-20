package com.example.RestaurantManagement.service;

import com.example.RestaurantManagement.dto.TableRequestDto;
import com.example.RestaurantManagement.dto.TableResponseDto;
import com.example.RestaurantManagement.entity.DineTable;
import com.example.RestaurantManagement.entity.RestaurantBranch;
import com.example.RestaurantManagement.entity.User;
import com.example.RestaurantManagement.enums.Role;
import com.example.RestaurantManagement.enums.TableStatus;
import com.example.RestaurantManagement.exception.BadRequestException;
import com.example.RestaurantManagement.exception.ResourceNotFoundException;
import com.example.RestaurantManagement.repository.BranchRepository;
import com.example.RestaurantManagement.repository.DineTableRepository;
import com.example.RestaurantManagement.repository.RestaurantRepository;
import com.example.RestaurantManagement.repository.UserRepository;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.RestaurantManagement.dto.TableResponseDto.*;

@Service
@RequiredArgsConstructor
//Manager cretate a table
public class TableService {
    private final UserRepository userRepository;
    private  final DineTableRepository tableRepository;
    private final BranchRepository branchRepository;

//manager will create the table
public TableResponseDto create(TableRequestDto dto,String managerEmail){
  RestaurantBranch branch=branchRepository.findById(dto.getBranchId())
          .orElseThrow(()->new ResourceNotFoundException("Branch wiuth this Id is invalid"));
     validateManager(managerEmail,branch);
if(tableRepository.existsByBranchIdAndTableNumber(dto.getBranchId(),dto.getTableNumber())) {
       throw new BadRequestException("Table number already exists in this branch");
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
            .orElseThrow(() -> new ResourceNotFoundException("Table not founded"));
    validateManager(managerEmail, table.getBranch());
    if (table.getStatus() == TableStatus.OCCUPIED) {

        throw new BadRequestException("Occupies table cannt be deleted");
    }
    tableRepository.delete(table);
}
  //view tabes can be viewd by superadmin owner and manger
   public List <TableResponseDto> getByBranch(Long branchId,String requesterEmail){
 branchRepository.findById(branchId)
         .orElseThrow(()->new ResourceNotFoundException("Branch not found") );
validateViewAccess(requesterEmail,branchId);

return tableRepository.findByBranchId(branchId)
        .stream().map(this :: toDto).collect(Collectors.toList());
    }

    //view single tabale
    public TableResponseDto getById(Long id,String requesterEmail){
 DineTable table= tableRepository.findById(id)
         .orElseThrow(()-> new ResourceNotFoundException("Table wit this id not found"));

validateViewAccess(requesterEmail,table.getBranch().getId());
return toDto(table);
    }
//logic for validate manger
    private void   validateManager(String managerEmail,RestaurantBranch branch) {
        User user = userRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("USer not found"));
        if (user.getBranch() == null || user.getBranch().getId() == null) {
            throw new ResourceNotFoundException("Branch not found");
        }        if (user.getRole() != Role.MANAGER) {
            throw new BadRequestException("Only manager can view this table!! THis role cant");
        }
    }
private void validateViewAccess(String requesterEMail,Long branchId) {
    User user = userRepository.findByEmail(requesterEMail)
            .orElseThrow(() -> new ResourceNotFoundException("This mail doenst exists"));
    if (user.getRole() == Role.SUPER_ADMIN)
        return;//can view all

    if (user.getRole() == Role.OWNER) {
        RestaurantBranch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        if (!branch.getRestaurant().getOwner().getEmail().equals(requesterEMail)) {
            throw new BadRequestException("You do not own this restaurant");
        }
        return;
    }
    if (user.getBranch() == null || !user.getBranch().getId().equals(branchId)) {
        throw new BadRequestException("You do not own this branch");
    }


}
    private TableResponseDto toDto(DineTable dt){
return TableResponseDto.builder()
        .id(dt.getId())
        .tableNumber(dt.getTableNumber())
         .status(dt.getStatus().name())
         .branchId(dt.getBranch().getId())
       .branchName(dt.getBranch().getBranchName())
         .build();
    }

}


