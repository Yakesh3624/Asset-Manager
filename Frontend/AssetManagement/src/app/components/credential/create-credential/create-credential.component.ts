import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Credential } from 'src/app/models/credential.model';
import { CredentialService } from 'src/app/services/credential/credential.service';

@Component({
  selector: 'app-create-credential',
  templateUrl: './create-credential.component.html',
  styleUrls: ['./create-credential.component.css']
})
export class CreateCredentialComponent implements OnInit {

  submitted: boolean = false;
  myForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private service: CredentialService) { }

  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      userName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(16), Validators.pattern('^[a-zA-Z0-9_.-]+$')]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20)]]
    })
  }

  submit(credential: Credential) {
    credential.usersId = Number(localStorage.getItem("usersId"));
    this.service.addData(credential).subscribe({
      next: () => {
        this.submitted = !this.submitted;
        this.router.navigate(['login'])
      }
    });


  }
}
