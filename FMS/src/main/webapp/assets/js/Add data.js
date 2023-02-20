function saveDetails(){
	 let productCode = $("#productCode").val();
	 let productName = $("#productName").val();
     if(productCode == null || productCode == "" || productCode == undefined){
          $("#msgs").text("Please Enter product Code");
          $("#warningModal").modal("show");
          return false;
      }
     if(productName == null || productName == "" || productName == undefined){
         $("#msgs").text("Please Enter product Name");
         $("#warningModal").modal("show");
         return false;
     }else{
		 $('#AddDataModal').modal('show');}
 }
 