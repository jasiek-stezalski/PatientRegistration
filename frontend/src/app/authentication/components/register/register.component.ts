import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';
import {User} from '../../../models/user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;

  constructor(public accountService: AccountService, public router: Router) {
  }

  ngOnInit() {
  }

  register() {
    if (this.user.password == this.user.confirmPassword) {
      this.accountService.createAccount(this.user).subscribe(() => {
          this.router.navigate(['/login']);
        }, err => {
          console.log(err);
          this.errorMessage = 'Błąd : Taki użytkownik już istnieje';
        }
      );
    } else {
      this.errorMessage = 'Błąd : Hasła muszą być takie same';
    }
  }

}
