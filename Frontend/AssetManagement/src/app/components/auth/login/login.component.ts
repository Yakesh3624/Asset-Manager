import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { timer } from 'rxjs';
import { Credential } from 'src/app/models/credential.model';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private router: Router, private service: AuthService) { }

  myForm!: FormGroup;
  isError: boolean = false;
  role: string = '';

  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      userName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(16)]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20)]]
    });
  }


  dashboard(credential: Credential) {
    this.service.login(credential).subscribe({
      next: (res) => {
        console.log("JWT Token:", res.token);
        this.service.saveToken(res.token);
        const role = this.service.getUserRole();
        if (role === 'ADMIN') {
          this.service.getUsersId(credential.userName).subscribe({
            next: (cred) => {
              const usersId = cred.usersId;
              localStorage.setItem("usersId", usersId.toString());
              localStorage.setItem("username", cred.userName.toString());
              this.router.navigate(["/admin-dashboard"]);
            }
          });
        } else if (role === 'EMPLOYEE') {

          this.service.getUsersId(credential.userName).subscribe({
            next: (cred) => {
              const usersId = cred.usersId;
              localStorage.setItem("usersId", usersId.toString());
              localStorage.setItem("username", cred.userName.toString());
              this.router.navigate(["employee-dashboard"]);
            }
          });
        }
      }, error: (err) => { this.isError = true; timer(1200).subscribe(() => this.isError = false) }
    });
  }
  // console.log(credential);
  // this.service.login(credential).subscribe((token)=>console.log(token));

  // if (this.role == "Admin")
  //   this.router.navigate(['admin-dashboard']);
  // else
  // this.router.navigate(['employee-dashboard']);
}
