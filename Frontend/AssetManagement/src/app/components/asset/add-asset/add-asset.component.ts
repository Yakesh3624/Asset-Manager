import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { timer } from 'rxjs';
import { Asset } from 'src/app/models/asset.model';
import { AssetService } from 'src/app/services/asset/asset.service';

@Component({
  selector: 'app-add-asset',
  templateUrl: './add-asset.component.html',
  styleUrls: ['./add-asset.component.css']
})
export class AddAssetComponent implements OnInit {

  myForm!: FormGroup;
  submitted: boolean = false;
  base64Image: string = '';
  isError: boolean = false;
  today: string = new Date().toISOString().split('T')[0];
  constructor(private formBuilder: FormBuilder, private service: AssetService) { }

  ngOnInit(): void {
    this.myForm = this.formBuilder.group({

      assetName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20), Validators.pattern('^[a-zA-Z0-9 ]+$')]],
      category: ['', [Validators.required]],
      assetModel: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10), Validators.pattern("^[a-zA-Z0-9.-]+$")]],
      manufacturingDate: ['', [Validators.required]],
      expiryDate: [''],
      assetValue: ['', [Validators.required, Validators.min(1)]],
      availability: ['available', Validators.required],
      image: [null]
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


  submit(asset: Asset) {
    asset.image = this.base64Image;
    this.service.addData(asset).subscribe({
      next: () => {
        this.submitted = true;
        timer(1200).subscribe(() => this.submitted = false);
        this.myForm.reset();
      }, error: (err) => { this.isError = true; timer(1200).subscribe(() => this.isError = false) }
    });

  }


}
