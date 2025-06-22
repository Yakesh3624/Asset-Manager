import { Component } from '@angular/core';
import { ServiceRequest } from 'src/app/models/serviceRequest.model';
import { ServiceRequestService } from 'src/app/services/service_request/service-request.service';

@Component({
  selector: 'app-view-service-request',
  templateUrl: './view-service-request.component.html',
  styleUrls: ['./view-service-request.component.css']
})
export class ViewServiceRequestComponent {

  data: ServiceRequest[] = [];
  filteredData: ServiceRequest[] = []
  searchTerm: string = '';

  constructor(private service: ServiceRequestService) { }

  ngOnInit(): void {
    this.getAllData();

  }

  getAllData() {
    this.service.getAllData().subscribe((data) => { this.data = data; this.filteredData = this.data; })
  }

  search() {
    const term = this.searchTerm.toLowerCase().trim();
    this.filteredData = this.data.filter(request =>
      request.requestId.toString().includes(term) ||
      request.status.toLowerCase().includes(term) ||
      request.issueType.toLowerCase().includes(term)
    );
  }
  reject(requestId: number, status: string) {
    this.service.updateData(requestId, status).subscribe({ next: () => this.getAllData() })
  }
  accept(requestId: number, status: string) {
    this.service.updateData(requestId, status).subscribe({ next: () => this.getAllData() })
  }

}
