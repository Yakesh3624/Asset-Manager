import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { timer } from 'rxjs';
import { Asset } from 'src/app/models/asset.model';
import { AssetService } from 'src/app/services/asset/asset.service';

@Component({
  selector: 'app-view-asset',
  templateUrl: './view-asset.component.html',
  styleUrls: ['./view-asset.component.css']
})
export class ViewAssetComponent implements OnInit {


  data!: Asset[];
  myForm!: FormGroup;
  isUpdate: boolean = false;
  updated: boolean = false;
  searchTerm: string = '';
  filteredData: Asset[] = [];
  assetNo!: number;
  base64Image: string = '';
  today: string = new Date().toISOString().split('T')[0];
  isDeleted: boolean = false;

  constructor(private formBuilder: FormBuilder, private service: AssetService) { }

  ngOnInit(): void {

    this.getAllData();

    this.myForm = this.formBuilder.group({

      assetName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20)]],
      category: ['', [Validators.required]],
      assetModel: ['', [Validators.required, Validators.minLength(1)]],
      manufacturingDate: ['', [Validators.required]],
      assetValue: ['', [Validators.required, Validators.min(1)]],
      availability: ['available', Validators.required]
    });
  }

  getAllData() {
    this.service.getAllData().subscribe((data) => { this.data = data; this.filteredData = this.data; });
  }
  selectedAsset: any = null;

  viewInfo(asset: Asset) {
    this.selectedAsset = asset;
  }
  infoClose() {
    this.selectedAsset = null;
  }
  updateButton(asset: Asset) {
    this.isUpdate = !this.isUpdate;

    this.assetNo = asset.assetNo;
    this.base64Image = asset.image;

    this.myForm.patchValue({
      assetName: asset.assetName,
      category: asset.category,
      assetModel: asset.assetModel,
      manufacturingDate: new Date(asset.manufacturingDate).toISOString().slice(0, 10),
      expiryDate: new Date(asset.expiryDate).toISOString().slice(0, 10),
      assetValue: asset.assetValue,
      availability: asset.availability,
      image: asset.image

    })
  }
  deleteAsset(assetNo: number) {
    this.service.deleteData(assetNo).subscribe({ next: () => { this.getAllData(); this.isDeleted = true; timer(1200).subscribe(() => this.isDeleted = false) } });
  }


  updateAsset(asset: Asset) {
    asset.assetNo = this.assetNo;
    asset.image = this.base64Image;

    this.service.updateData(asset).subscribe({
      next: () => {
        this.getAllData();
        this.updated = true;
        timer(1500).subscribe(() => this.updated = false);
      }
    });

  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.base64Image = (reader.result as string).split(',')[1];
    };
    reader.readAsDataURL(file);
  }

  updateClose() {
    this.isUpdate = false;
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

  getImage(image: any) {
    return 'data:image/jpeg;base64,' + image;
  }
}
