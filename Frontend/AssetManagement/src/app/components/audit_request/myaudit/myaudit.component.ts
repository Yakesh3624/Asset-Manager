import { Component } from '@angular/core';
import { AuditRequest } from 'src/app/models/auditRequest.model';
import { AuditRequestService } from 'src/app/services/audit_request/audit-request.service';

@Component({
  selector: 'app-myaudit',
  templateUrl: './myaudit.component.html',
  styleUrls: ['./myaudit.component.css']
})
export class MyauditComponent {

  data: AuditRequest[] = [];
  filteredData: AuditRequest[] = []
  searchTerm: string = '';

  constructor(private service: AuditRequestService) { }

  ngOnInit(): void {
    this.getAllData();
  }

  search() {
    const term = this.searchTerm.toLowerCase().trim();
    this.filteredData = this.data.filter(request =>
      request.assetNo.toString() === term ||
      request.status.toLowerCase().startsWith(term)
    );
  }

  getAllData() {
    let usersId = Number(localStorage.getItem("usersId"));
    this.service.getDataByUsersId(usersId).subscribe((data) => { this.data = data; this.filteredData = this.data; });
  }
  reject(requestId: number, status: string) {
    this.service.updateData(requestId, status).subscribe({ next: () => this.getAllData() });
  }
  approve(requestId: number, status: string) {
    this.service.updateData(requestId, status).subscribe({ next: () => this.getAllData() });
  }
}
