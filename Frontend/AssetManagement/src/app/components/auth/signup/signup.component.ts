import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { timer } from 'rxjs';
import { Users } from 'src/app/models/users.model';
import { SignupService } from 'src/app/services/signup/signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  myForm!: FormGroup;
  submitted: boolean = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private service: SignupService) { }

  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(26), Validators.pattern('^[a-zA-Z]+$')]],
      lastName: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(26), Validators.pattern('^[a-zA-Z]+$')]],
      role: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      gender: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern("^[0-9]{10}$")]],
      address: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(50)]]
    });
  }

  submit(users: Users) {
    this.service.addData(users).subscribe({
      next: (data) => {
        console.log(data);
        this.submitted = true;
        timer(1200).subscribe(() => this.submitted = false);
        this.myForm.reset();
        localStorage.setItem("usersId", data.usersId.toString());
        this.router.navigate(['/credential']);
      }
    });

  }

}
