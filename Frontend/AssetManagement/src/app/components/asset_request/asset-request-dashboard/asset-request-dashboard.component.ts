import { Component } from '@angular/core';

@Component({
  selector: 'app-asset-request-dashboard',
  templateUrl: './asset-request-dashboard.component.html',
  styleUrls: ['./asset-request-dashboard.component.css']
})
export class AssetRequestDashboardComponent {

  isProfileBarClicked: boolean = false;

  profileClicked() {
    this.isProfileBarClicked = !this.isProfileBarClicked;
  }


}
