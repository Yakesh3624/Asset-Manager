import { Component } from '@angular/core';
import { timer } from 'rxjs';
import { ServiceRequest } from 'src/app/models/serviceRequest.model';
import { ServiceRequestService } from 'src/app/services/service_request/service-request.service';

@Component({
  selector: 'app-myservice',
  templateUrl: './myservice.component.html',
  styleUrls: ['./myservice.component.css']
})
export class MyserviceComponent {

  data: ServiceRequest[] = [];
  filteredData: ServiceRequest[] = []
  searchTerm: string = '';
  usersId!: number;
  isCancel: boolean = false;

  constructor(private service: ServiceRequestService) { }

  ngOnInit(): void {
    this.usersId = Number(localStorage.getItem("usersId"));
    this.getAllData();

  }

  getAllData() {
    this.service.getDataByUsersId(this.usersId).subscribe({
      next: (data) => { this.data = data; this.filteredData = this.data; },
      error: (err) => this.filteredData = []
    })
  }

  search() {
    const term = this.searchTerm.toLowerCase().trim();
    this.filteredData = this.data.filter(request =>
      request.requestId.toString() === term ||
      request.status.toLowerCase().startsWith(term) ||
      request.issueType.toLowerCase().startsWith(term)
    );

  }

  cancel(requestId: number) {
    this.service.deleteData(requestId).subscribe({ next: () => { this.getAllData(); this.isCancel = true; timer(1200).subscribe(() => this.isCancel = false) } })
  }


}
