import { Component } from '@angular/core';

@Component({
  selector: 'app-audit-request-dashboard',
  templateUrl: './audit-request-dashboard.component.html',
  styleUrls: ['./audit-request-dashboard.component.css']
})
export class AuditRequestDashboardComponent {

  isProfileBarClicked: boolean = false;

  profileClicked() {
    this.isProfileBarClicked = !this.isProfileBarClicked;
  }

}
