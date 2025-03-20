import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideToastr } from 'ngx-toastr';

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection(
    { eventCoalescing: true }),
    provideRouter(routes),
    provideAnimations(),
    provideToastr(/*{
      positionClass: 'toast-top-center', // Ajusta la posición de los mensajes
      timeOut: 3000, // Duración de los mensajes
      preventDuplicates: true, // Evita mensajes duplicados
    }*/),
    provideClientHydration(withEventReplay()),
    provideHttpClient(withFetch())
  ]
};
