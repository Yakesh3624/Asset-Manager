import { Component } from '@angular/core';
import { timer } from 'rxjs';
import { AuditRequest } from 'src/app/models/auditRequest.model';
import { AuditRequestService } from 'src/app/services/audit_request/audit-request.service';

@Component({
  selector: 'app-view-audit-request',
  templateUrl: './view-audit-request.component.html',
  styleUrls: ['./view-audit-request.component.css']
})
export class ViewAuditRequestComponent {


  data: AuditRequest[] = [];
  isDeleted: boolean = false;

  constructor(private service: AuditRequestService) { }

  ngOnInit(): void {
    this.getAllData();
  }

  filteredData: AuditRequest[] = []
  searchTerm: string = '';
  search() {
    const term = this.searchTerm.toLowerCase().trim();
    this.filteredData = this.data.filter(request =>
      request.requestId.toString().includes(term) ||
      request.status.toLowerCase().includes(term)
    );
  }

  getAllData() {
    this.service.getAllData().subscribe({
      next: (data) => { this.data = data; this.filteredData = this.data; },
      error: (err) => { this.filteredData = [] }
    });
    // this.data=this.data.filter(request=>request.status.includes("Pending"));
  }
  reject(requestId: number, status: string) {
    this.service.updateData(requestId, status).subscribe({ next: () => this.getAllData() });
  }
  approve(requestId: number, status: string) {
    this.service.updateData(requestId, status).subscribe({ next: () => this.getAllData() });
  }
  delete(requestId: number) {
    this.service.deleteData(requestId).subscribe({ next: () => { this.getAllData(); this.isDeleted = true; timer(1200).subscribe(() => this.isDeleted = false) } });
  }


}
