import { Routes } from '@angular/router';
import path from 'path';
import { authGuard } from './core/guards/auth.guard';
import { authenticatedGuard } from './core/guards/authenticated.guard';

export const routes: Routes = [

    {
        path: '',
        loadComponent: () => import('./shared/components/layout/layout.component'),
        children: [
            {
                path: 'dashboard',
                loadComponent: () => import('./business/dashboard/dashboard.component'),
                canActivate: [authGuard]
            },
            {
                path: 'insumos',
                loadComponent: () => import('./business/insumos/insumos.component'),
                canActivate: [authGuard]
            },
            {
                path: 'maestros',
                loadComponent: () => import('./business/maestros/maestros.component'),
                canActivate: [authGuard]
            },
            {
                path: 'proyectos',
                loadComponent: () => import('./business/proyectos/proyectos.component'),
                canActivate: [authGuard]
            },
            {
                path: 'proveedores',
                loadComponent: () => import('./business/proveedores/proveedores.component'),
                canActivate: [authGuard]
            },
            {
                path: '',
                redirectTo: 'dashboard',
                pathMatch: 'full'
            }
        ]
    },
    {
        path: 'login',
        loadComponent: ()=> import ('./business/authentication/login/login.component'),
        canActivate: [authenticatedGuard]
    },
    {
        path: 'contrasena',
        loadComponent: () => import('./business/authentication/contrasena/contrasena.component')
    },
    {
        path: '**',
        redirectTo: 'dashboard'
    }
];
