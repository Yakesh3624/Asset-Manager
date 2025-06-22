import { Component } from '@angular/core';

@Component({
  selector: 'app-service-request-dashboard',
  templateUrl: './service-request-dashboard.component.html',
  styleUrls: ['./service-request-dashboard.component.css']
})
export class ServiceRequestDashboardComponent {
  isProfileBarClicked: boolean = false;

  profileClicked() {
    this.isProfileBarClicked = !this.isProfileBarClicked;
  }

}
