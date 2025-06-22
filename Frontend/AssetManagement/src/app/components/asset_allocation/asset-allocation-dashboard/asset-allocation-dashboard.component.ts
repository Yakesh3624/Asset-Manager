import { Component } from '@angular/core';

@Component({
  selector: 'app-asset-allocation-dashboard',
  templateUrl: './asset-allocation-dashboard.component.html',
  styleUrls: ['./asset-allocation-dashboard.component.css']
})
export class AssetAllocationDashboardComponent {

  isProfileBarClicked: boolean = false;

  profileClicked() {
    this.isProfileBarClicked = !this.isProfileBarClicked;
  }

}
