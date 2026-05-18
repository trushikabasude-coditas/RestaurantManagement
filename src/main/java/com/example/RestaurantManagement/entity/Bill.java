package com.example.RestaurantManagement.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private Long restaurant_id;
 private int subTotal;
 private int discountApplied;
 private String discountReason;
private int taxAmount;//(subtotal-=disc)
private int gstRate;
private int FinalAmount;
@Column(name = "pdf_url")
    private String pdfUrl;

//let us do all things with bit phase wise 1st we will do the entity and all te mappings
    //ow  ner of restaurant ahs many brnaches and each branch has the manager and th restaurenat with the owner like main restaurnat the owner is manger itself and all brnaces has the 1 1 mnaer )
    (so think very clearly tdraightky and proper with all validation like every existig validation)
    then we will do restaurnat module like adding and all things of restaurant ,branches and all then at last we will do roles things
so login with jwt token also that we will crete one and then have password with that password role will login and get token and then only securely loged in . (common way of token can use refresh token and all for robust login)

i have entites and all see my updated zip see ecah and evry small thin clearly so that full fletch all above requiremnts are achived
}

