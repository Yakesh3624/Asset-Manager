import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/auth/login/login.component';
import { AdminDashboardComponent } from './components/dashboard/admin-dashboard/admin-dashboard.component';
import { AddAssetComponent } from './components/asset/add-asset/add-asset.component';
import { AssetDashboardComponent } from './components/asset/asset-dashboard/asset-dashboard.component';
import { ViewAssetComponent } from './components/asset/view-asset/view-asset.component';
import { ReactiveFormsModule } from "@angular/forms";
import { ProfileSidebarComponent } from './components/profile/profile-sidebar/profile-sidebar.component';
import { FormsModule } from '@angular/forms';
import { AssetRequestDashboardComponent } from './components/asset_request/asset-request-dashboard/asset-request-dashboard.component';
import { ViewAssetRequestComponent } from './components/asset_request/view-asset-request/view-asset-request.component';
import { ServiceRequestDashboardComponent } from './components/service_request/service-request-dashboard/service-request-dashboard.component';
import { ViewServiceRequestComponent } from './components/service_request/view-service-request/view-service-request.component';
import { AuditRequestDashboardComponent } from './components/audit_request/audit-request-dashboard/audit-request-dashboard.component';
import { ViewAuditRequestComponent } from './components/audit_request/view-audit-request/view-audit-request.component';
import { AssetAllocationDashboardComponent } from './components/asset_allocation/asset-allocation-dashboard/asset-allocation-dashboard.component';
import { ViewAssetAllocationComponent } from './components/asset_allocation/view-asset-allocation/view-asset-allocation.component';
import { SignupComponent } from './components/auth/signup/signup.component';
import { UsersDashboardComponent } from './components/users/users-dashboard/users-dashboard.component';
import { ViewUsersComponent } from './components/users/view-users/view-users.component';
import { CreateCredentialComponent } from './components/credential/create-credential/create-credential.component';
import { EmployeeDashboardComponent } from './components/dashboard/employee-dashboard/employee-dashboard.component';
import { EmployeeViewAssetComponent } from './components/asset/view-asset-employee/employee-view-asset.component';
import { EmployeeMyassetComponent } from './components/asset/myasset/employee-myasset.component';
import { MyserviceComponent } from './components/service_request/myservice/myservice.component';
import { MyauditComponent } from './components/audit_request/myaudit/myaudit.component';
import { MyrequestComponent } from './components/asset_request/myrequest/myrequest.component';
import { HttpClientModule } from "@angular/common/http";
import { ProfilePageComponent } from './components/profile/profile-page/profile-page.component'
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminDashboardComponent,
    AddAssetComponent,
    AssetDashboardComponent,
    ViewAssetComponent,
    ProfileSidebarComponent,
    AssetRequestDashboardComponent,
    ViewAssetRequestComponent,
    ServiceRequestDashboardComponent,
    ViewServiceRequestComponent,
    AuditRequestDashboardComponent,
    ViewAuditRequestComponent,
    AssetAllocationDashboardComponent,
    ViewAssetAllocationComponent,
    SignupComponent,
    UsersDashboardComponent,
    ViewUsersComponent,
    CreateCredentialComponent,
    EmployeeDashboardComponent,
    EmployeeViewAssetComponent,
    EmployeeMyassetComponent,
    MyserviceComponent,
    MyauditComponent,
    MyrequestComponent,
    ProfilePageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
