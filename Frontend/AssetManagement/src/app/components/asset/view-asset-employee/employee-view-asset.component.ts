import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { timer } from 'rxjs';
import { Asset } from 'src/app/models/asset.model';
import { AssetRequest } from 'src/app/models/assetRequest.model';
import { AssetRequestCreate } from 'src/app/models/assetRequestCreate.model';
import { AssetService } from 'src/app/services/asset/asset.service';
import { AssetRequestService } from 'src/app/services/asset_request/asset-request.service';

@Component({
  selector: 'app-employee-view-asset',
  templateUrl: './employee-view-asset.component.html',
  styleUrls: ['./employee-view-asset.component.css']
})
export class EmployeeViewAssetComponent {

  data!: Asset[];
  myForm!: FormGroup;
  isUpdate: boolean = false;
  updated: boolean = false;
  searchTerm: string = '';
  filteredData: Asset[] = [];
  selectedAsset: any = null;
  isError: boolean = false;
  isRequested: boolean = false;

  constructor(private formBuilder: FormBuilder, private service: AssetService, private request: AssetRequestService) { }

  getAllData() {
    this.service.getAllData().subscribe({
      next: (data) => { this.data = data.filter(asset => asset.availability.startsWith("available")); this.filteredData = this.data; },
      error: (err) => this.filteredData = []
    });
  }

  viewInfo(asset: Asset) {
    this.selectedAsset = asset;
  }
  infoClose() {
    this.selectedAsset = null;
  }

  ngOnInit(): void {

    this.getAllData();
  }

  search() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredData = this.data.filter(asset =>
      asset.assetNo.toString() === term ||
      asset.category.toLowerCase().startsWith(term) ||
      asset.availability.toLowerCase().startsWith(term) ||
      asset.assetName.toLowerCase().includes(term)
    );
    if (term == null) {
      this.filteredData = this.data;
    }

  }
  requestAsset(assetNo: number) {
    let usersId = Number(localStorage.getItem("usersId"));
    let request: AssetRequestCreate = {
      assetNo: assetNo,
      usersId: usersId,
      status: "Pending"
    }
    this.request.addData(request).subscribe({
      next: () => { this.isRequested = true; timer(1200).subscribe(() => this.isRequested = false) },
      error: (err) => { this.isError = true; timer(1200).subscribe(() => this.isError = false) }
    });
  }

  getImage(image: any) {
    return 'data:image/jpeg;base64,' + image;
  }
}
