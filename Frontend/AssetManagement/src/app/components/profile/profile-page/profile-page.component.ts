import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Users } from 'src/app/models/users.model';
import { UsersService } from 'src/app/services/users/users.service';
import { Credential } from 'src/app/models/credential.model';
import { timer } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent {

  data!: Users;
  username!: string | null;
  myForm!: FormGroup;
  isUpdate: boolean = false;
  updated: boolean = false;
  usersId!: number;

  constructor(private formBuilder: FormBuilder, private location: Location, private router: Router, private service: UsersService, private auth: AuthService) { }
  ngOnInit(): void {
    this.usersId = Number(localStorage.getItem("usersId"));
    this.getDataByUsersId();
    this.myForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(10), Validators.pattern('^[a-zA-Z]+$')]],
      lastName: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10), Validators.pattern('^[a-zA-Z]+$')]],
      role: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      gender: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern("^[0-9]{10}$")]],
      address: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(50)]]
    });
  }


  logout() {
    this.auth.logout();
  }

  back() {
    this.location.back();
  }

  getDataByUsersId() {
    this.username = localStorage.getItem("username");
    this.service.getDataByUsersId(this.usersId).subscribe((data) => { this.data = data })
  }

  updateButton(users: Users) {
    this.isUpdate = !this.isUpdate;
    this.usersId = users.usersId;
    this.myForm.patchValue({
      firstName: users.firstName,
      lastName: users.lastName,
      role: users.role,
      email: users.email,
      gender: users.gender,
      contactNumber: users.contactNumber,
      address: users.address
    })
  }

  updateUsers(users: Users) {
    users.usersId = this.usersId;
    users.role = users.role.toUpperCase();
    this.service.updateData(users).subscribe({
      next: () => {
        this.getDataByUsersId();
        this.updated = true;
        timer(1500).subscribe(() => this.updated = false);
      }
    });
  }
  updateClose() {
    this.isUpdate = false;
  }

}
