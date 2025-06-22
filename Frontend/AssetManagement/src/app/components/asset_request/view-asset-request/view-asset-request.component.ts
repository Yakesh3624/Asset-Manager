import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { timer } from 'rxjs';
import { Asset } from 'src/app/models/asset.model';
import { AssetRequest } from 'src/app/models/assetRequest.model';
import { AssetRequestService } from 'src/app/services/asset_request/asset-request.service';

@Component({
  selector: 'app-view-asset-request',
  templateUrl: './view-asset-request.component.html',
  styleUrls: ['./view-asset-request.component.css']
})
export class ViewAssetRequestComponent implements OnInit {

  data: AssetRequest[] = [];
  filteredData: AssetRequest[] = []
  searchTerm: string = '';

  constructor(private service: AssetRequestService) { }

  ngOnInit(): void {
    this.getAllData();
  }

  getAllData() {
    this.service.getAllData().subscribe({
      next: (data) => { this.data = data.filter(request => request.status.startsWith("Pending") || request.status.startsWith("Rejected")); this.filteredData = this.data; },
      error: (err) => this.filteredData = []
    });
  }
  search() {
    const term = this.searchTerm.toLowerCase().trim();
    this.filteredData = this.data.filter(request =>
      request.requestId.toString().includes(term) ||
      request.status.toLowerCase().includes(term)
    );
  }
  reject(requestId: number, status: string) {
    this.service.updateData(requestId, status).subscribe({ next: () => this.getAllData() })

  }
  accept(requestId: number, status: string) {
    this.service.updateData(requestId, status).subscribe({ next: () => this.getAllData() })
  }

}
