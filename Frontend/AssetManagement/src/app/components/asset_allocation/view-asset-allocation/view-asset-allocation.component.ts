import { Component, OnInit } from '@angular/core';
import { timer } from 'rxjs';
import { AssetAllocation } from 'src/app/models/assetAllocation.model';
import { AssetAllocationService } from 'src/app/services/asset_allocation/asset-allocation.service';
import { AuditRequestService } from 'src/app/services/audit_request/audit-request.service';

@Component({
  selector: 'app-view-asset-allocation',
  templateUrl: './view-asset-allocation.component.html',
  styleUrls: ['./view-asset-allocation.component.css']
})
export class ViewAssetAllocationComponent implements OnInit {

  data: AssetAllocation[] = [];
  filteredData: AssetAllocation[] = []
  searchTerm!: number;
  isDeleted: boolean = false;
  isRequested: boolean = false;
  isError: boolean = false;

  constructor(private service: AssetAllocationService, private auditService: AuditRequestService) { }
  ngOnInit(): void {
    this.getAllData();
  }

  getAllData() {
    this.service.getAllData().subscribe({
      next: (data) => { this.data = data; this.filteredData = this.data; },
      error: (err) => { this.filteredData = [] }
    });
  }
  search() {
    const term = this.searchTerm;
    this.filteredData = this.data.filter(allocation =>
      allocation.allocationId === term
    );
  }
  auditRequest(assetAllocation: AssetAllocation) {
    console.log(assetAllocation);
    this.auditService.addData(assetAllocation).subscribe({
      next: () => { this.isRequested = true; timer(1200).subscribe(() => this.isRequested = false) },
      error: (err) => { this.isError = true; timer(1200).subscribe(() => this.isError = false) }
    });
  }
  delete(assetNo: number) {
    this.service.deleteData(assetNo).subscribe({ next: () => { this.getAllData(); this.isDeleted = true; timer(1200).subscribe(() => this.isDeleted = false) } })
  }

}
