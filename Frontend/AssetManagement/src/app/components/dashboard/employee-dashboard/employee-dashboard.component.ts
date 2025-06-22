import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-employee-dashboard',
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent {

  isProfileBarClicked: boolean = false;

  constructor(private service: AuthService) { }

  profileClicked() {
    this.isProfileBarClicked = !this.isProfileBarClicked;
  }

  logout() {
    this.service.logout();
  }

}
