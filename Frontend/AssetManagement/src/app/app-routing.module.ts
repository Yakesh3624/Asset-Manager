import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { AdminDashboardComponent } from './components/dashboard/admin-dashboard/admin-dashboard.component';
import { AssetDashboardComponent } from './components/asset/asset-dashboard/asset-dashboard.component';
import { AddAssetComponent } from './components/asset/add-asset/add-asset.component';
import { ViewAssetComponent } from './components/asset/view-asset/view-asset.component';
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
import { ProfilePageComponent } from './components/profile/profile-page/profile-page.component';

const routes: Routes = [

  { path: "login", component: LoginComponent },
  { path: "signup", component: SignupComponent },
  { path: "credential", component: CreateCredentialComponent },
  { path: "", redirectTo: "login", pathMatch: 'full' },
  { path: "admin-dashboard", component: AdminDashboardComponent, data: { role: 'ADMIN' } },
  {
    path: "asset-dashboard", component: AssetDashboardComponent,
    children: [{ path: "add", component: AddAssetComponent },
    { path: "view", component: ViewAssetComponent },
    { path: "", redirectTo: "view", pathMatch: 'full' }]
  },
  {
    path: "asset-request-dashboard", component: AssetRequestDashboardComponent,
    children: [
      { path: "view", component: ViewAssetRequestComponent },
      { path: "", redirectTo: "view", pathMatch: 'full' }
    ]
  },
  {
    path: "service-request-dashboard", component: ServiceRequestDashboardComponent,
    children: [
      { path: "view", component: ViewServiceRequestComponent },
      { path: "", redirectTo: "view", pathMatch: 'full' }
    ]
  },
  {
    path: "audit-request-dashboard", component: AuditRequestDashboardComponent,
    children: [
      { path: "view", component: ViewAuditRequestComponent },
      { path: "", redirectTo: "view", pathMatch: 'full' }
    ]
  },
  {
    path: "asset-allocation-dashboard", component: AssetAllocationDashboardComponent,
    children: [
      { path: "view", component: ViewAssetAllocationComponent },
      { path: "", redirectTo: "view", pathMatch: 'full' }
    ]
  },
  {
    path: "users-dashboard", component: UsersDashboardComponent,
    children: [
      { path: "view", component: ViewUsersComponent },
      { path: "", redirectTo: "view", pathMatch: 'full' }
    ]
  },
  {
    path: "employee-dashboard", component: EmployeeDashboardComponent, data: { role: 'EMPLOYEE' },
    children: [
      { path: "asset", component: EmployeeViewAssetComponent },
      { path: "myasset", component: EmployeeMyassetComponent },
      { path: "", redirectTo: 'myasset', pathMatch: 'full' },
      { path: "myservice", component: MyserviceComponent },
      { path: "myaudit", component: MyauditComponent },
      { path: "myrequests", component: MyrequestComponent }
    ]
  },
  { path: "profile", component: ProfilePageComponent }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
