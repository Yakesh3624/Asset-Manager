import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { timer } from 'rxjs';
import { AssetRequest } from 'src/app/models/assetRequest.model';
import { AssetRequestService } from 'src/app/services/asset_request/asset-request.service';

@Component({
  selector: 'app-myrequest',
  templateUrl: './myrequest.component.html',
  styleUrls: ['./myrequest.component.css']
})
export class MyrequestComponent {


  data: AssetRequest[] = [];
  disabled: boolean = true;
  isCancel: boolean = false;
  usersId!: number;
  filteredData: AssetRequest[] = []
  searchTerm: string = '';
  constructor(private service: AssetRequestService) { }

  ngOnInit(): void {
    this.usersId = Number(localStorage.getItem("usersId"));
    this.getAllData();
  }

  getAllData() {
    this.service.getDataByUsersId(this.usersId).subscribe({
      next: (data) => { this.data = data; this.filteredData = this.data; },
      error: (err) => this.filteredData = []
    });
  }

  search() {
    const term = this.searchTerm.toLowerCase().trim();
    this.filteredData = this.data.filter(request =>
      request.requestId.toString() === term ||
      request.status.toLowerCase().startsWith(term)
    );
  }

  cancel(requestId: number) {

    this.service.deleteData(requestId).subscribe({ next: () => { this.getAllData(); this.isCancel = true; timer(1200).subscribe(() => this.isCancel = false) } });

  }

}
