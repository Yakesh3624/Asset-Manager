import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { timer } from 'rxjs';
import { Asset } from 'src/app/models/asset.model';
import { AssetAllocation } from 'src/app/models/assetAllocation.model';
import { AssetRequestCreate } from 'src/app/models/assetRequestCreate.model';
import { ServiceRequest } from 'src/app/models/serviceRequest.model';
import { ServiceRequestCreate } from 'src/app/models/serviceRequestCreate.model';
import { MyassetService } from 'src/app/services/asset/myasset.service';
import { AssetAllocationService } from 'src/app/services/asset_allocation/asset-allocation.service';
import { AssetRequestService } from 'src/app/services/asset_request/asset-request.service';
import { ServiceRequestService } from 'src/app/services/service_request/service-request.service';

@Component({
  selector: 'app-employee-myasset',
  templateUrl: './employee-myasset.component.html',
  styleUrls: ['./employee-myasset.component.css']
})
export class EmployeeMyassetComponent implements OnInit {

  data: AssetAllocation[] = [];
  myForm!: FormGroup;
  isRequest: boolean = false;
  isRequested: boolean = false;
  searchTerm: string = '';
  filteredData: AssetAllocation[] = [];
  selectedAsset: any = null;
  usersId!: number;
  assetNo!: number;
  isReturned: boolean = false;
  isError: boolean = false;

  constructor(private formBuilder: FormBuilder, private allocation: AssetAllocationService, private service: ServiceRequestService) { }

  ngOnInit(): void {
    this.usersId = Number(localStorage.getItem("usersId"));
    this.getAllData();

    this.myForm = this.formBuilder.group({

      issueType: ['', [Validators.required]],
      description: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]]
    })

  }

  getAllData() {

    this.allocation.getDataByUsersId(this.usersId).subscribe((data) => { this.data = data; this.filteredData = this.data })
  }

  placeRequest(data: any) {

    let serviceRequest: ServiceRequestCreate = {
      description: data.description,
      issueType: data.issueType,
      status: "Pending",
      assetNo: this.assetNo,
      usersId: this.usersId
    }
    console.log(serviceRequest);
    this.service.addData(serviceRequest).subscribe({
      next: () => {
        this.isRequested = true;
        timer(1200).subscribe(() => { this.isRequested = false; this.isRequest = false });
      },
      error: (err) => { this.isError = true; }
    });
    this.myForm.reset();

  }
  requestClose() {

    this.isRequest = false;
    this.isError = false;

  }
  serviceRequestButton(assetNo: number) {
    this.isRequest = !this.isRequest;
    this.assetNo = assetNo;
  }
  returnButton(assetNo: number) {

    this.allocation.deleteData(assetNo).subscribe({
      next: () => { this.getAllData(); this.isReturned = true; timer(1200).subscribe(() => this.isReturned = false) },
      error: (err) => this.filteredData = []
    });

  }

  search() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredData = this.data.filter(asset =>
      asset.assetNo === Number(term)
    );
    if (term == null) {
      this.filteredData = this.data;
    }

  }


}
