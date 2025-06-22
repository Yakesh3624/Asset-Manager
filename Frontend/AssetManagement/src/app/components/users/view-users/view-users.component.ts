import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { timer } from 'rxjs';
import { Users } from 'src/app/models/users.model';
import { UsersService } from 'src/app/services/users/users.service';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {

  data: Users[] = [];
  myForm!: FormGroup;
  isUpdate: boolean = false;
  updated: boolean = false;
  filteredData: Users[] = []
  searchTerm: string = '';
  usersId!: number;
  isDeleted: boolean = false;


  constructor(private formBuilder: FormBuilder, private service: UsersService) { }

  ngOnInit(): void {
    this.getAllData()
    this.myForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(26), Validators.pattern('^[a-zA-Z]+$')]],
      lastName: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10), Validators.pattern('^[a-zA-Z]+$')]],
      role: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      gender: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern("^[0-9]{10}$")]],
      address: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(50)]]
    });
  }

  getAllData() {
    this.service.getAllData().subscribe({
      next: (data) => { this.data = data; this.filteredData = this.data; },
      error: (err) => { this.filteredData = [] }
    })
  }

  search() {
    const term = this.searchTerm.toLowerCase().trim();
    if (term !== '') {
      this.filteredData = this.data.filter(users =>
        users.usersId.toString() === term ||
        users.firstName.toLowerCase().includes(term) ||
        users.role.toLowerCase() === term ||
        users.gender.toLowerCase() === term ||
        users.email === term
      );
    }
    else {
      this.getAllData();
    }
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
        this.getAllData();
        this.updated = true;
        timer(1500).subscribe(() => this.updated = false);
      }
    });
  }
  updateClose() {
    this.isUpdate = false;
  }

  deleteUsers(usersId: number) {
    this.service.deleteData(usersId).subscribe({
      next: () => { this.getAllData(); this.isDeleted = true; timer(1200).subscribe(() => { this.isDeleted = false }) },
      error: (err) => { this.getAllData() }
    });
  }
}
