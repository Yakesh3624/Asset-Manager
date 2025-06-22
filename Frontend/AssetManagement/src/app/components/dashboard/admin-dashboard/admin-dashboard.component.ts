import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  isProfileBarClicked: boolean = false;
  dashboardInfo: any[] = [];

  constructor(private servive: AuthService) { }
  ngOnInit(): void {

    this.dashboardInfo = [
      {
        title: 'Asset Management',
        description: 'Add, edit, or delete assets. Ensure all data is valid and assets are categorized properly.',
        tip: 'Go to Asset > Add Asset to create a new asset entry.'
      },
      {
        title: 'Asset Requests',
        description: 'Manage asset requests raised by employees. You can approve or reject them as needed.',
        tip: 'Navigate to Asset Requests to view and manage the requests.'
      },
      {
        title: 'Asset Allocation',
        description: 'Allocate available assets to employees. Only one user can hold an asset at a time.',
        tip: 'Use the Asset Allocation module to assign assets.'
      },
      {
        title: 'Audit Requests',
        description: 'Review audit submissions made by users and track their audit status.',
        tip: 'Check the Audit Requests tab for submitted audits.'
      },
      {
        title: 'Service Requests',
        description: 'Update and manage maintenance/service requests related to assets.',
        tip: 'Visit Service Requests to change service statuses.'
      },
      {
        title: 'User Management',
        description: 'Add, update, or remove users and assign roles.',
        tip: 'Manage user records under the User module.'
      },
    ];
  }



  profileClicked() {
    this.isProfileBarClicked = !this.isProfileBarClicked;
  }

  logout() {
    this.servive.logout();
  }
}
